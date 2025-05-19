import domain.usecase.DetermineAuctionWinnerUseCase

fun main() {
    val determineAuctionWinner = DetermineAuctionWinnerUseCase()

    val result = determineAuctionWinner()

    if (result != null) {
        println("Winner: ${result.winner}")
        println("Winning Price: ${result.winningPrice} euros")
    } else {
        println("No valid bids above reserve price.")
    }
}
