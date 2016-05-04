package com.stxnext.stxinsider.estimote;

import android.content.Context;
import android.util.Log;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.estimote.sdk.Utils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class NearestBeaconManager {

    private static final String TAG = "NearestBeaconManager";

    private static final Region ALL_ESTIMOTE_BEACONS = new Region("all Estimote beacons", null, null, null);

    private List<BeaconID> beaconIDs;

    private Listener listener;

    private BeaconID currentlyNearestBeaconID;
    private Deque<BeaconID> previousDetectedNearestBeaconsIDs = new LinkedList<BeaconID>();
    private boolean firstEventSent = false;

    private BeaconManager beaconManager;

    final int HISTORY_SIZE = 4;

    public NearestBeaconManager(Context context, List<BeaconID> beaconIDs) {
        this.beaconIDs = beaconIDs;

        beaconManager = new BeaconManager(context);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                checkForNearestBeacon(list);
            }
        });
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public interface Listener {
        void onNearestBeaconChanged(BeaconID beaconID);
    }

    public void startNearestBeaconUpdates() {
        resetState();
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
            }
        });
    }

    private synchronized void resetState() {
        currentlyNearestBeaconID = null;
        previousDetectedNearestBeaconsIDs.clear();
    }

    public void stopNearestBeaconUpdates() {
        beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
    }

    public void destroy() {
        beaconManager.disconnect();
    }

    private void checkForNearestBeacon(List<Beacon> allBeacons) {
        List<Beacon> beaconsOfInterest = filterOutBeaconsByIDs(allBeacons, beaconIDs);
        Beacon nearestBeacon = findNearestBeacon(beaconsOfInterest);
        BeaconID nearestBeaconID = null;
        if (nearestBeacon != null) {
            nearestBeaconID = BeaconID.fromBeacon(nearestBeacon);
            if (isBeaconDetectionConfirmed(nearestBeaconID) &&
                    (!nearestBeaconID.equals(currentlyNearestBeaconID) || !firstEventSent))
                    updateNearestBeacon(nearestBeaconID);
        } else if (currentlyNearestBeaconID != null || !firstEventSent) {
            updateNearestBeacon(null);
        }
        addToDetectedBeaconsHistory(nearestBeaconID);
    }

    /**
     * Confirms detection by checking if before readings were the same.
     */
    private synchronized boolean isBeaconDetectionConfirmed(BeaconID nearestBeaconID) {
        if (nearestBeaconID == null || previousDetectedNearestBeaconsIDs.size() < HISTORY_SIZE)
            return false;
        for (BeaconID historyBeaconID : previousDetectedNearestBeaconsIDs) {
            if (!nearestBeaconID.equals(historyBeaconID))
                return false;
        }
        return true;
    }

    private synchronized void addToDetectedBeaconsHistory(BeaconID beaconID) {
        while (previousDetectedNearestBeaconsIDs.size() > HISTORY_SIZE)
            previousDetectedNearestBeaconsIDs.removeLast();
        previousDetectedNearestBeaconsIDs.addFirst(beaconID);
    }

    private void updateNearestBeacon(BeaconID beaconID) {
        currentlyNearestBeaconID = beaconID;
        firstEventSent = true;
        if (listener != null) {
            listener.onNearestBeaconChanged(beaconID);
        }
    }

    private static List<Beacon> filterOutBeaconsByIDs(List<Beacon> beacons, List<BeaconID> beaconIDs) {
        List<Beacon> filteredBeacons = new ArrayList<>();
        for (Beacon beacon : beacons) {
            BeaconID beaconID = BeaconID.fromBeacon(beacon);
            if (beaconIDs.contains(beaconID)) {
                filteredBeacons.add(beacon);
            }
        }
        return filteredBeacons;
    }

    private static Beacon findNearestBeacon(List<Beacon> beacons) {
        Beacon nearestBeacon = null;
        double nearestBeaconsDistance = -1;
        for (Beacon beacon : beacons) {
            double distance = Utils.computeAccuracy(beacon);
            if (distance > -1 &&
                    (distance < nearestBeaconsDistance || nearestBeacon == null)) {
                nearestBeacon = beacon;
                nearestBeaconsDistance = distance;
            }
        }

        Log.d(TAG, "Nearest beacon: " + nearestBeacon + ", distance: " + nearestBeaconsDistance);
        return nearestBeacon;
    }
}
