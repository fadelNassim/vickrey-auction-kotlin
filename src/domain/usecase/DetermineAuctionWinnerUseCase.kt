package domain.usecase

import data.datasource.AuctionDataSource

data class AuctionResult(
    val winner: String,
    val winningPrice: Int
)

/**
 * Represents the rules of a Vickrey auction (second-price sealed-bid auction):
 *
 * - Each bidder submits their bids privately (others cannot see them).
 * - There is a reserve price: the minimum amount the seller is willing to accept.
 * - The highest bid (that meets or exceeds the reserve price) wins the item.
 * - The winner does not pay their own bid amount.
 *   Instead, they pay the highest valid bid from a non-winning bidder.
 * - If no other valid bid exists, the winner pays the reserve price.
 */
class DetermineAuctionWinnerUseCase {
    var repository = AuctionDataSource()

    operator fun invoke(): AuctionResult? {
        val bidders = repository.bidders
        val reservePrice = repository.reservePrice

        // Flatten all bids with associated bidder names, and filter by reserve price
        val allValidBids = bidders.flatMap { bidder ->
            bidder.bids.map { bid -> bidder.name to bid }
        }.filter { (_, bid) -> bid >= reservePrice }

        // If there are no valid bids, the auction fails
        if (allValidBids.isEmpty()) return null

        // Sort all valid bids in descending order
        val sortedBids = allValidBids.sortedByDescending { it.second }

        // Determine the winner: the first occurrence of the highest bid
        val (winnerName, highestBid) = sortedBids.first()

        // Collect all non-winning bids by filtering out the winner's bids
        val nonWinningBids = sortedBids.filter { it.first != winnerName }

        // Determine the winning price: highest bid among non-winners or reserve price
        val winningPrice = nonWinningBids.maxOfOrNull { it.second } ?: reservePrice

        // Return the auction result
        return AuctionResult(winner = winnerName, winningPrice = winningPrice)
    }
}