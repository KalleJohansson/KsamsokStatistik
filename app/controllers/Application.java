package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
    	Logger.info("AllIndexUniqueValue: %s",AllIndexUniqueValueCount.count());
    	Logger.info("Facet: %s", Facet.count());
    	Logger.info("GetGeoResource: %s", GetGeoResource.count());
    	Logger.info("GetRelations: %s", GetRelations.count());
    	Logger.info("GetRelationTypes: %s", GetRelationTypes.count());
    	Logger.info("GetServiceOrganization: %s", GetServiceOrganization.count());
    	Logger.info("Rss: %s", Rss.count());
    	Logger.info("Search: %s", Search.count());
    	Logger.info("SearchHelp: %s", SearchHelp.count());
    	Logger.info("Statistic: %s", Statistic.count());
    	Logger.info("StatisticSearcj: %s", StatisticSearch.count());
    	Logger.info("Stem: %s", Stem.count());
        render();
    }

}