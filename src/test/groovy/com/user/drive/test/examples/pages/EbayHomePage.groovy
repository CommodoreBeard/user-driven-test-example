package com.user.drive.test.examples.pages

import com.user.drive.test.examples.pages.modules.SearchModule
import geb.Page

class EbayHomePage extends Page {

    static url = "/"

    static at = { title.contains("Electronics, Cars, Fashion, Collectibles, Coupons and More | eBay") }

    static content = {
        search { module SearchModule }
    }
}
