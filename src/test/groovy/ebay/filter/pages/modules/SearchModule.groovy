package ebay.filter.pages.modules

import geb.Module

class SearchModule extends Module {

    static content = {
        searchTextBox     { $("input", id:"gh-ac") }
        searchButton      { $("input", id:"gh-btn") }
        catagoryContainer { $("select", id:"gh-cat") }
    }

    void searchFor(String input) {
        searchTextBox << input
        searchButton.click()
    }

    void selectCatagory(Integer catagory) {
        catagoryContainer.click()
        catagoryContainer.find("option").find { it.value() == "${catagory}" }.click()
    }

    void searchFor(String input, Integer catagory) {
        selectCatagory(catagory)
        searchFor(input)
    }

}
