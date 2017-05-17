package ebay.filter

import ebay.filter.domain.CatagoryKeywords
import ebay.filter.domain.SearchCatagoryOptions
import ebay.filter.pages.EbayHomePage
import ebay.filter.pages.SearchResultsPage
import ebay.filter.specification.EbayGebSpecification
import spock.lang.Unroll

class SearchWithinCatagorySpec extends EbayGebSpecification {

    @Unroll
    def "Searching within a catagory should limit the types of listings returned"() {

        given: "As a user I navigate to the Ebay Home Page"
        to EbayHomePage

        when: "I Search for a listing within a catagory"
        search.searchFor "iPhone", catagory

        then: "I am directed to the search results page"
        at SearchResultsPage

        when: "I collect the number of listings with a matching key word"
        def matchingResults = searchResultListings.collect { listing ->
            listing.titleContains(keyword)
        }

        then: "At least one listing on the first page contains a relevant word in the title"
        matchingResults.size() > 0

        where:
        catagory                                          | keyword
        SearchCatagoryOptions.BOOK_COMICS_AND_MAGAZINES   | CatagoryKeywords.BOOK_COMICS_AND_MAGAZINES
        SearchCatagoryOptions.ART                         | CatagoryKeywords.ART
        SearchCatagoryOptions.JEWELERY_AND_WATCHES        | CatagoryKeywords.JEWELERY_AND_WATCHES

    }
}
