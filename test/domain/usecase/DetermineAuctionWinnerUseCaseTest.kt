package domain.usecase

import domain.model.Bidder
import data.datasource.AuctionDataSource
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNotNull
import junit.framework.TestCase.assertNull
import org.junit.Before
import org.junit.Test

class DetermineAuctionWinnerUseCaseTest {

    private lateinit var useCase: DetermineAuctionWinnerUseCase
    private lateinit var repository: AuctionDataSource

    @Before
    fun setUp() {
        useCase = DetermineAuctionWinnerUseCase()
        repository = AuctionDataSource()
        useCase.repository = repository
    }

    @Test
    fun `should return winner and second-highest price`() {
        val bidders = listOf(
            Bidder("A", listOf(110, 130)),
            Bidder("B", listOf()),
            Bidder("C", listOf(125)),
            Bidder("D", listOf(105, 115, 90)),
            Bidder("E", listOf(132, 135, 140))
        )
        repository.bidders = bidders
        repository.reservePrice = 100

        val result = useCase()
        assertNotNull(result)
        assertEquals("E", result?.winner)
        assertEquals(130, result?.winningPrice)
    }

    @Test
    fun `should handle tie by selecting first in order`() {
        val bidders = listOf(
            Bidder("A", listOf(120)),
            Bidder("B", listOf(120)),
            Bidder("C", listOf(100))
        )
        repository.bidders = bidders
        repository.reservePrice = 100

        val result = useCase()
        assertNotNull(result)
        assertEquals("A", result?.winner)
        assertEquals(120, result?.winningPrice)
    }

    @Test
    fun `should return null if no valid bids`() {
        val bidders = listOf(
            Bidder("X", listOf(50, 80)),
            Bidder("Y", listOf(70)),
            Bidder("Z", listOf(90))
        )
        repository.bidders = bidders
        repository.reservePrice = 100

        val result = useCase()
        assertNull(result)
    }

    @Test
    fun `should handle single valid bidder`() {
        val bidders = listOf(
            Bidder("Solo", listOf(150))
        )
        repository.bidders = bidders
        repository.reservePrice = 100

        val result = useCase()
        assertNotNull(result)
        assertEquals("Solo", result?.winner)
        assertEquals(100, result?.winningPrice)
    }

    @Test
    fun `should handle empty bidders list`() {
        repository.bidders = emptyList()
        repository.reservePrice = 100

        val result = useCase()
        assertNull(result)
    }
}
