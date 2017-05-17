package ebay.filter.pages

import ebay.filter.domain.SortOptions
import ebay.filter.pages.modules.SearchResultListingModule
import ebay.filter.pages.modules.SortResultsModule
import geb.Page
import geb.navigator.Navigator

class SearchResultsPage extends Page {

    static at = {
        waitFor { $("div", id:"cbrt") }
    }

    static content = {
        sort                      { module SortResultsModule }
        listingFilterContainer    { $("div", class:"pnl-b") }
        searchResultListings      { $("li", class:"lvresult").collect { it.module(SearchResultListingModule) } }
        pages                     { $("td", class:"pages") }
    }

    void sortBy(Integer sortOptionValue) {
        sort.changeSortPreference(sortOptionValue)
    }

    void filterBy(String listingType) {
        listingFilterContainer.find(class:"tgl_button").find { it.text().contains(listingType) }.click()
    }

    void gotoPage(Integer pageNumber) {
        pages.find("a", class:"pg").find { it.text() == pageNumber.toString() }.click()
    }

    List<Navigator> singlePriceListings() {
        searchResultListings.findAll { listing ->
            listing.singlePrice()
        }
    }

    List<BigDecimal> resultPrices() {
        singlePriceListings().collect { listing ->
            listing.listingPrice()
        }
    }

    List<BigDecimal> resultPriceAndPostages() {
        singlePriceListings().collect { listing ->
            listing.listingPriceAndPostage()
        }
    }

    Boolean isSortedBy(Integer sortOptionValue) {
        switch (sortOptionValue) {
            case SortOptions.LOW_PRICE:
                resultPrices() == resultPrices().sort()
                break
            case SortOptions.HIGH_PRICE:
                resultPrices() == resultPrices().sort().reverse()
                break
            case SortOptions.LOW_PRICE_AND_PP:
                resultPriceAndPostages() == resultPriceAndPostages().sort()
                break
            case SortOptions.HIGH_PRICE_AND_PP:
                resultPriceAndPostages() == resultPriceAndPostages().sort().reverse()
                break
            default:
                false
        }
    }
}
