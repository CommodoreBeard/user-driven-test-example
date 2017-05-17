package ebay.filter.pages.modules

import geb.Module

class SortResultsModule extends Module {

    static content = {
        sortByContainer(required: true) { $("div", id:"DashSortByContainer") }
        sortMenu                        { $("ul", id:"SortMenu") }
    }

    void changeSortPreference(Integer searchOptionValue) {
        sortByContainer.click()
        sortMenu.find("a").find { it.value() == "${searchOptionValue}" }.click()
    }
}