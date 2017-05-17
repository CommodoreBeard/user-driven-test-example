package ebay.filter

import ebay.filter.pages.EbayHomePage
import ebay.filter.pages.SearchResultsPage
import ebay.filter.domain.ListingFilterOptions
import ebay.filter.domain.SortOptions
import ebay.filter.specification.EbayGebSpecification
import spock.lang.Unroll


class SearchFilterAndSortSpec extends EbayGebSpecification {

    @Unroll
    def "Results sorted and filtered in any combination should have a price and shipping info"() {

        given: "As a user I navigate to the Ebay Home Page"
        to EbayHomePage

        when: "I Search for an listing"
        search.searchFor "iPhone"

        then: "I am directed to the search results page"
        at SearchResultsPage

        when: "I filter the results by #listingType and sort by #sortType"
        filterBy(listingType)
        sortBy(sortType)

        then: "The first listing has a price"
        singlePriceListings().first().listingPrice() >= minimumPrice
        singlePriceListings().first().freePostage() || singlePriceListings().first().postagePrice() >= minimumPrice

        where:
        listingType                        | sortType
        ListingFilterOptions.BUY_IT_NOW    | SortOptions.LOW_PRICE
        ListingFilterOptions.AUCTION       | SortOptions.ENDING_SOONEST
        ListingFilterOptions.ALL_LISTINGS  | SortOptions.HIGH_PRICE_AND_PP
    }

    @Unroll
    def "Results filtered by listing type 'BUY IT NOW' should not contain bid information"() {

        given: "As a user I navigate to the Ebay Home Page"
        to EbayHomePage

        when: "I Search for an listing"
        search.searchFor "iPhone"

        then: "I am directed to the search results page"
        at SearchResultsPage

        when: "I filter the results by Buy it now and sort by #sortType"
        filterBy(ListingFilterOptions.BUY_IT_NOW)
        sortBy(sortType)

        then: "The first listing is a buy it now listing"
        singlePriceListings().first().isBuyItNow()

        where: sortType << [
            //SortOptions.LOW_PRICE,
            SortOptions.NEWEST,
            //SortOptions.HIGH_PRICE_AND_PP
                ]
    }

    @Unroll
    def "Results filtered by listing type 'Auction' should contain bid information"() {

        given: "As a user I navigate to the Ebay Home Page"
        to EbayHomePage

        when: "I Search for an listing"
        search.searchFor "iPhone"

        then: "I am directed to the search results page"
        at SearchResultsPage

        when: "I filter the results by Buy it now and sort by #sortType"
        filterBy(ListingFilterOptions.AUCTION)
        sortBy(sortType)

        then: "The first listing is not a buy it now listing"

        and: "The number of bids is present"
        singlePriceListings().first().numberOfBids() >= 0

        where: sortType << [
                SortOptions.LOW_PRICE,
                SortOptions.ENDING_SOONEST,
                SortOptions.HIGH_PRICE_AND_PP
        ]

    }

    @Unroll
    def "Filtering search results by #listingType and sorting by #sortBy should return results in order"() {

        given: "As a user I navigate to the Ebay Home Page"
        to EbayHomePage

        when: "I Search for an listing"
        search.searchFor "iPhone"

        then: "I am directed to the search results page"
        at SearchResultsPage

        when: "I filter the results by #listingType and sort by #sortType"
        filterBy(listingType)
        sortBy(sortType)

        then: "The listings are sorted correctly"
        searchResultListings.size() > 1
        isSortedBy(sortType)

        where:
        listingType                        | sortType
        ListingFilterOptions.BUY_IT_NOW    | SortOptions.LOW_PRICE
        ListingFilterOptions.ALL_LISTINGS  | SortOptions.HIGH_PRICE_AND_PP
    }
}
