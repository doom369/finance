package com.ddumanskiy.web.handlers;

import com.ddumanskiy.web.controllers.FinanceUAController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * Created by ddumanskiy
 * Date : 12/19/2014.
 */
@Path("/api")
public class AverageHandler {

    private static final Logger log = LogManager.getLogger(AverageHandler.class);

    private FinanceUAController financeUAController = new FinanceUAController();

    @GET
    @Produces("text/plain")
    @Path("realAverage")
    public String getRealAverage() {
        Double result = financeUAController.getAverage();
        if (result == null) {
            return "";
        }
        return String.format("%.2f", result);
    }

}
