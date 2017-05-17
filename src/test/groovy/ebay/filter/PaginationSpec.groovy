package ebay.filter

import ebay.filter.pages.EbayHomePage
import ebay.filter.pages.SearchResultsPage
import ebay.filter.specification.EbayGebSpecification

class PaginationSpec extends EbayGebSpecification {

    def "For a search term which results in multiple pages I can load later paged results"() {

        given: "As a user I navigate to the Ebay Home Page"
        to EbayHomePage

        when: "I Search for a listing with a known large result set"
        search.searchFor "iPhone"

        then: "I am directed to the search results page"
        at SearchResultsPage

        when: "I store the price of first listing on page 1"
        def pageOneListingPrice = singlePriceListings().first().listingPrice()

        and: "I click page 4 of the page selector"
        gotoPage(4)

        then: "I am directed to another page of results"
        at SearchResultsPage

        and: "The first listing price from page one does not match that on page 4"
        pageOneListingPrice != singlePriceListings().first().listingPrice()
    }
}
