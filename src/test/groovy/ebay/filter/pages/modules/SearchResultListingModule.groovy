package ebay.filter.pages.modules

import geb.Module

class SearchResultListingModule extends Module {

    static final String POUNDSYMBOL = "\u00A3"

    static content = {
        title                                  { $("h3", class:"lvtitle").find("a").attr("title") }
        price                                  { $("li", class:"lvprice") }
        format                                 { $("li", class: "lvformat") }
        shippingFee(required: false)           { $("li", class:"lvshipping").find("span", class:"fee").text() }
        buyItNowLogo(required: false)          { $("span", title:"Buy it now") }
        buyItNowBestOfferLogo(required: false) { $("span", title:"Buy it now or Best Offer") }
    }

    Integer numberOfBids() {
        format.text()
                .replace(" ", "")
                .replace("bids", "")
                .replace("bid", "")
                .toInteger()
    }

    String formatPrice(String elementText) {
        elementText
                .replace(POUNDSYMBOL, "")
                .replace(",", "")
                .replace("+", "")
                .replace("postage", "")
                .replaceAll(" ", "")
    }

    BigDecimal listingPrice() {
        String priceText = formatPrice(price.find("span").text())
        new BigDecimal(priceText)
    }

    BigDecimal postagePrice() {
        freePostage()? new BigDecimal(0) : new BigDecimal(formatPrice(shippingFee))
    }

    BigDecimal listingPriceAndPostage() {
        new BigDecimal(listingPrice() + postagePrice())
    }

    Boolean singlePrice() {
        price.find("span").collect().size() == 1
    }

    Boolean freePostage() {
        !shippingFee
    }

    Boolean isBuyItNow() {
        buyItNowLogo || buyItNowBestOfferLogo
    }

    Boolean titleContains(String searchTerm) {
        title? title.contains(searchTerm) : false
    }
}
