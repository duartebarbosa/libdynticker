package mobi.boilr.libdynticker.exchanges;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mobi.boilr.libdynticker.core.Exchange;
import mobi.boilr.libdynticker.core.Pair;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

public final class JustcoinExchange extends Exchange {

	public JustcoinExchange(long expiredPeriod) {
		super("Justcoin", expiredPeriod);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected List<Pair> getPairsFromAPI() throws IOException {
		List<Pair> pairs = new ArrayList<Pair>();
		String addr = "https://justcoin.com/api/v1/markets";
		Iterator<JsonNode> elements = new ObjectMapper().readTree(new URL(addr)).getElements();
		for(String id; elements.hasNext();) {
			id = elements.next().get("id").getTextValue();
			pairs.add(new Pair(id.substring(0, 3), id.substring(3, 6)));
		}
		return pairs;
	}

	@Override
	protected String getTicker(Pair pair) throws IOException {
		String addr = "https://justcoin.com/api/v1/markets";
		JsonNode node = new ObjectMapper().readTree(new URL(addr));
		return parseJSON(node, pair);
	}

	@Override
	public String parseJSON(JsonNode node, Pair pair) throws IOException {
		Iterator<JsonNode> elements = node.getElements();
		while(elements.hasNext()) {
			JsonNode next = elements.next();
			String id = next.get("id").getTextValue();
			if(id.toLowerCase().equals(pair.getCoin().toLowerCase() + pair.getExchange().toLowerCase())) {
				return next.get("last").getTextValue();
			}
			// pairs.add(new Pair(node.getTextValue().substring(0,3),
			// node.getTextValue().substring(3,3)));
		}
		throw new IOException("Pair not found");
	}

}
