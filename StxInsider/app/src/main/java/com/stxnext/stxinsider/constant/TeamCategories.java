package com.stxnext.stxinsider.constant;

import com.stxnext.stxinsider.model.TeamCatergoryHeader;

/**
 * Created by bkosarzycki on 22.01.16.
 */
public class TeamCategories {
    public static TeamCatergoryHeader[] teams = new TeamCatergoryHeader[] {
            new TeamCatergoryHeader().header("Meet the teams").footer("See what types of projects and what kinds of technologies we deal within an interactive tour through our company"),
            new TeamCatergoryHeader().header("Data Mining Solutions").imagePath("/nopath").footer("Lorem ipsum"),
            new TeamCatergoryHeader().header("Financial Anylysis Tools").imagePath("/nopath").footer("Lorem ipsum")
    };
}
