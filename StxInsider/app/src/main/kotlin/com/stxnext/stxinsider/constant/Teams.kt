package com.stxnext.stxinsider.constant

import com.stxnext.stxinsider.model.SliderItem
import com.stxnext.stxinsider.model.Category

/**
 * Created by  Bartosz Kosarzycki on 23.02.16.
 */
object Teams {
    @JvmField
    val teams = arrayOf(
            SliderItem().header("Hogarth").imagePath("teams/background/hogarth_bckg.jpg")
                    .description("The client is a leading marketing agency capable of crafting advertising and other marketing\n" +
                        "communications for industry clients across all media and languages. Due to talent constraints,\n" +
                        "the client sought a offshore technology partner with whom they could utilize as an ongoing\n" +
                        "Python development resource. STX Next was one of several offshore development firms to\n" +
                        "receive a request for proposal from the client, and they were selected based on their relevant\n" +
                        "experience, demonstrated Python expertise and price point. Once commissioned, STX Next\n" +
                        "began to collaborate closely with the client on a number of custom development initiatives, which\n" +
                        "includes the following services: Requirements gathering, project scoping, custom functional\n" +
                        "development (Python), ad hoc enhancements and modifications, user interface/user experience\n" +
                        "and performance testing, and ongoing support.").category(Category.MARKETING),
            SliderItem().header("Inteligo").imagePath("teams/background/inteligo_bckg.png")
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
                        "new account” forms sharing.").category(Category.BANKING),
            SliderItem().header("Decernis").imagePath("teams/background/decernis_bckg.jpg")
                    .description("The client is an information technology firm offering research and information systems to support\n" +
                        "global product safety compliance in food, beverages, consumer, and industrial products. The\n" +
                        "client sought a qualified technology partner to augment their internal development team for\n" +
                        "their various development initiatives. STX Next was one of several potential vendors to receive\n" +
                        "a statement of work from the client, and they were chosen as the solution partner based on\n" +
                        "their relevant experience and demonstrated technical expertise. Once commissioned, STX\n" +
                        "Next began collaborating with the client on a number of development projects, which required\n" +
                        "the following services: Requirements gathering, project scoping, strategic oversight, custom\n" +
                        "backend development (Python/Plone), data preparation and migration, systems integration and\n" +
                        "configuration, information architecture, business logic validation, user interface/user experience\n" +
                        "and performance testing, training, and ongoing support.").category(Category.DATA_MINING),
            SliderItem().header("SOFTAX").imagePath("teams/background/sotftax_bckg.png")
                    .description("The client is Softax, a computer software company that offers business technology solutions to\n" +
                        "corporations in the financial sector. Due to limited internal capabilities, the client sought a qualified\n" +
                        "technology partner to support their custom development projects. STX Next was one of a handful\n" +
                        "of potential vendors to receive an inquiry and statement of work from the client, and they were\n" +
                        "chosen as the solution partner based on their demonstrated capabilities and previous experience.\n" +
                        "Once commissioned, STX Next began to collaborate closely with the client on their various\n" +
                        "technology projects, which involved the following: User requirements gathering, project scoping,\n" +
                        "strategic oversight, data preparation and migration, custom functional development, information\n" +
                        "architecture, business logic validation, user interface/user experience and performance testing,\n" +
                        "training, maintenance, and ongoing support.\n" +
                        "During projects development, various tools are used: Vagrant, Jenkins, Tipboard, Gunnery,\n" +
                        "Py.tests, Fixtures, Mock.").category(Category.BANKING))
}
