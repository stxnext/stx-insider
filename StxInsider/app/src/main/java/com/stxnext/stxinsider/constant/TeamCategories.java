package com.stxnext.stxinsider.constant;

import com.stxnext.stxinsider.model.TeamCategory;
import com.stxnext.stxinsider.model.TeamCategoryHeader;

/**
 * Created by bkosarzycki on 22.01.16.
 */
public class TeamCategories {
    public static TeamCategoryHeader[] teams = new TeamCategoryHeader[] {
            new TeamCategoryHeader().header("Meet the teams").imagePath("teams/meet_the_teams.png")
                    .footer("See what types of projects and what kinds of technologies we deal within an interactive tour through our company")
                    .background("teams/teams_backg.jpg"),
            new TeamCategoryHeader().category(TeamCategory.MARKETING).header("Marketing").imagePath("teams/financial_analysis.jpg").footer("Lorem ipsum"),
            new TeamCategoryHeader().category(TeamCategory.BANKING).header("Banking").imagePath("teams/financial_analysis.jpg").footer("Lorem ipsum"),
            new TeamCategoryHeader().category(TeamCategory.DATA_MINING).header("Data Mining Solutions").imagePath("teams/data_mining.png").footer("Lorem ipsum"),
            new TeamCategoryHeader().category(TeamCategory.FINANCIAL_ANALYSIS).header("Financial Analysis Tools").imagePath("teams/financial_analysis.jpg").footer("Lorem ipsum")
    };
}
