package com.stxnext.stxinsider.constant;

import com.stxnext.stxinsider.model.Team;
import com.stxnext.stxinsider.model.TeamCategory;

/**
 * Created by bkosarzycki on 01.02.16.
 */
public class Teams {
    public static Team[] teams = new Team[] {
            new Team().header("Hogarth").imagePath("teams/financial_analysis.png")
                    .description("The client is a leading marketing agency capable of crafting advertising and other marketing\n" +
                            "communications for industry clients across all media and languages. Due to talent constraints,\n" +
                            "the client sought a offshore technology partner with whom they could utilize as an ongoing\n" +
                            "Python development resource. STX Next was one of several offshore development firms to\n" +
                            "receive a request for proposal from the client, and they were selected based on their relevant\n" +
                            "experience, demonstrated Python expertise and price point. Once commissioned, STX Next\n" +
                            "began to collaborate closely with the client on a number of custom development initiatives, which\n" +
                            "includes the following services: Requirements gathering, project scoping, custom functional\n" +
                            "development (Python), ad hoc enhancements and modifications, user interface/user experience\n" +
                            "and performance testing, and ongoing support.")
                    .category(TeamCategory.MARKETING),
            new Team().header("Inteligo").imagePath("teams/financial_analysis.png")
                    .description("Inteligo is one of the largest banks in Poland. Our project was an informational website of Inteligo\n" +
                            "bank accounts providing updated information about sales promotions, special offers related to\n" +
                            "bank accounts, insurances or credit cards.\n\n" +
                            "" +
                            "The goal of the website was to provide the customer with a solution enabling advanced content\n" +
                            "management. To meet these needs a complex architecture was offered. The proposed CMS\n" +
                            "had to be adjusted so that it could generate statistical version of the content. The portal had\n" +
                            "to be resistant to a single breakdown point and integrated with client’s existing infrastructure.\n" +
                            "Consolidation of static content with a dynamic functionality was needed, along with search\n" +
                            "contents, comments or contact form. Integration with bank’s internal systems allowed for „create\n" +
                            "new account” forms sharing.")
                    .category(TeamCategory.BANKING)
    };
}
