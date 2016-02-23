package com.stxnext.stxinsider.constant

import com.stxnext.stxinsider.model.Category
import com.stxnext.stxinsider.model.TeamCategoryHeader

/**
 * Created by  Bartosz Kosarzycki on 23.02.16.
 */
object CategoryHeaders {
    @JvmField
    val teams = arrayOf(
            TeamCategoryHeader().header("Meet the teams").imagePath("teams/meet_the_teams.png").footer("See what types of projects and what kinds of technologies we deal within an interactive tour through our company").background("teams/teams_backg.png"),
            TeamCategoryHeader().category(Category.MARKETING).header("Marketing").imagePath("teams/financial_analysis.png").footer("Lorem ipsum"),
            TeamCategoryHeader().category(Category.BANKING).header("Banking").imagePath("teams/financial_analysis.png").footer("Lorem ipsum"),
            TeamCategoryHeader().category(Category.DATA_MINING).header("Data Mining Solutions").imagePath("teams/data_mining.png").footer("Lorem ipsum"),
            TeamCategoryHeader().category(Category.FINANCIAL_ANALYSIS).header("Financial Analysis Tools").imagePath("teams/financial_analysis.png").footer("Lorem ipsum"))

}
