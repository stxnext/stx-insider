package com.stxnext.stxinsider.constant

import android.text.SpannableString
import com.stxnext.stxinsider.view.model.DetailsItem

/**
 * Created by bkosarzycki on 17.02.16.
 */
object EventsData2016 {

    val data : List<DetailsItem<SpannableString>> = listOf(
            DetailsItem<SpannableString>("Wacław Zalewski & Henk van Leussen", "Presentation additional info",
                    SpannableString("<b>Abstract:</b> <br/> <br/> As nearshoring and outsourcing are becoming increasingly important components of the modern economy, we would like to address opportunities and business value Central European partners can provide. We we'll also discuss the current practice and share our thoughts on the future of this type of cooperation. <br/> <br/> <b>Wacław <br/> <br/> Bio: <br/> <br/> </b> Wacław having the pleasure of leading the magnificent Business Research Team, my responsibilities include, but are not limited to, prospecting, lead generation, CRM management and finding a way to get in touch with decision makers you need. I'm also unable to write about myself in third person.  <br/> <br/> <b>Henk <br/> <br/>  Bio: </b> <br/> <br/>  In my job, VP of Business Development, I’m managing all phases of business development, business planning, prospecting, building C-level relationships, detailed presentations and negotiations to closing and follow-up. I’m well in improving (administrative) processes, have a creative mind, think in solutions and may well convince. <br/> <br/>")),
            DetailsItem<SpannableString>("Name Surname", "Aditional text", SpannableString("Some spannable content <b> bold content </b>")),
            DetailsItem<SpannableString>("Name Surname", "Aditional text", SpannableString("Some spannable content <b> bold content </b>")),
            DetailsItem<SpannableString>("Name Surname", "Aditional text", SpannableString("Some spannable content <b> bold content </b>")),
            DetailsItem<SpannableString>("Name Surname", "Aditional text", SpannableString("Some spannable content <b> bold content </b>"))

    )
}