package com.ddumanskiy.web.controllers;

import com.ddumanskiy.web.enums.FinanceUABidType;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static com.ddumanskiy.web.enums.FinanceUABidType.ASK;
import static com.ddumanskiy.web.enums.FinanceUABidType.BID;

/**
 * Created by ddumanskiy
 * Date : 12/19/2014.
 *
 * Grabs all 'bids' info from finance.ua.
 */
public class FinanceUAController {

    private static final Logger log = LogManager.getLogger(FinanceUAController.class);

    private static final String BASE_URL = "http://tables.finance.ua/ua/currency/order";
    private static CloseableHttpClient httpclient = HttpClients.createDefault();

    public Double getAverage() {
        try {
            double averageMin = average(BID);
            double averageMax = average(ASK);
            log.debug("Average : {} - {}", averageMin, averageMax);
            return (averageMin + averageMax) / 2;
        } catch (Exception e) {
            log.error(e);
        }
        return null;
    }

    public static double average(FinanceUABidType financeUABidType) throws Exception {
        HttpPost post = new HttpPost(BASE_URL);

        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("xajax", "load"));
        nameValuePairs.add(new BasicNameValuePair("xajaxargs[]", "<xjxobj><e><k>event</k><v>type</v></e><e><k>location</k><v>ua,0,8</v></e><e><k>currency</k><v>USD</v></e><e><k>type</k><v>" + financeUABidType.name() + "</v></e><e><k>present</k><v></v></e></xjxobj>"));
        post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

        HttpResponse response = httpclient.execute(post);

        InputStream is = response.getEntity().getContent();

        Document doc = Jsoup.parse(is, "UTF-8", BASE_URL);

        String tableString = doc.select("cmd[t=order-store]").text();
        Document table = Jsoup.parse(tableString);

        int amountUSDSum = 0;
        double amountUAHSum = 0;

        for (Element tr : table.select("tr")) {
            double price = Double.parseDouble(tr.select("td[class=\"c3\"]").text());
            int amountUSD = Integer.parseInt(tr.select("td[class=\"c4\"]").text().replace(" ", ""));

            amountUSDSum += amountUSD;
            amountUAHSum += price * amountUSD;
        }

        return (amountUAHSum / amountUSDSum);
    }

}
