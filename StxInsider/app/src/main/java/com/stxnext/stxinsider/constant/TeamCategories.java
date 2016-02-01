package com.stxnext.stxinsider.constant;

import com.stxnext.stxinsider.model.TeamCatergoryHeader;

/**
 * Created by bkosarzycki on 22.01.16.
 */
public class TeamCategories {
    public static TeamCatergoryHeader[] teams = new TeamCatergoryHeader[] {
            new TeamCatergoryHeader().header("Meet the teams").imagePath("teams/meet_the_teams.png")
                    .footer("See what types of projects and what kinds of technologies we deal within an interactive tour through our company")
                    .withBackground("teams/teams_backg.jpg"),
            new TeamCatergoryHeader().header("Data Mining Solutions").imagePath("teams/data_mining.png").footer("Lorem ipsum"),
            new TeamCatergoryHeader().header("Financial Analysis Tools").imagePath("teams/financial_analysis.jpg").footer("Lorem ipsum")
    };
}
