package mobi.boilr.libdynticker.exchanges;

import java.io.File;
import java.io.IOException;
import java.util.List;

import mobi.boilr.libdynticker.core.ExchangeTest;
import mobi.boilr.libdynticker.core.Pair;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AllcoinExchangeTest extends ExchangeTest {

	@Override
	@Before
	public void setUp() throws Exception {
		testExchange = new AllcoinExchange(1000);
	}

	@Override
	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testParseJson() {
		try {
			Pair pair = new Pair("LTC", "BTC");
			JsonNode node = (new ObjectMapper().readTree(new File("src/test/json/allcoin-ticker.json")));
			String lastValue = testExchange.parseJSON(node, pair);
			Assert.assertEquals("0.01250000", lastValue);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
	}

	@Test
	public void testGetPairs() {
		List<Pair> pairs;
		try {
			pairs = testExchange.getPairs();
			Assert.assertTrue(pairs.contains(new Pair("DOGE", "BTC")));
			Assert.assertFalse(pairs.contains(new Pair("InvalidCoin", "BTC")));
		} catch (IOException e) {
			Assert.fail();
		}
	}
}
