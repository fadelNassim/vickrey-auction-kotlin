package data.datasource

import domain.model.Bidder

class AuctionDataSource {
    var bidders = listOf(
        Bidder("A", listOf(110, 130)),
        Bidder("B", listOf()),
        Bidder("C", listOf(125)),
        Bidder("D", listOf(105, 115, 90)),
        Bidder("E", listOf(132, 135, 140))
    )

    var reservePrice = 100
}