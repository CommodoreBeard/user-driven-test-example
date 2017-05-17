package ebay.filter.specification

import geb.spock.GebReportingSpec
import spock.lang.Shared

class EbayGebSpecification extends GebReportingSpec{

    @Shared final static String ebayHomeUrl = "/"
    @Shared final static BigDecimal minimumPrice = new BigDecimal(0.01)

}
