package controllers;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import models.AllIndexUniqueValueCount;
import models.Facet;
import models.GetGeoResource;
import models.GetRelationTypes;
import models.GetRelations;
import models.GetServiceOrganization;
import models.KsamsokStatConstants;
import models.Rss;
import models.SOCHObject;
import models.SearchHelp;
import models.Search;
import models.Statistic;
import models.StatisticSearch;
import models.Stem;
import play.Logger;
import play.Play;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class ReadLogs extends Job {

	public void doJob(){
		File logDir;
		if (Play.mode.isDev()){
			logDir=new File(Play.configuration.getProperty("access.log.file.dir.dev"));
		} else {
			logDir=new File(Play.configuration.getProperty("access.log.file.dir.dev"));
		}

			BufferedReader br = null;
			File logFile = null;
			try {
				logFile = new File("/home/kajoh/Dokument/Arbete/Ksamsök/loggar/localhost_access_log.2013-12-12.txt"/*output.txt"Play.configuration.getProperty("access.log.file.dir.dev")*/);
				FileInputStream fstream = new FileInputStream(logFile);
				DataInputStream in = new DataInputStream(fstream);
				br = new BufferedReader(new InputStreamReader(in));
				String logEntry;
				while ((logEntry = br.readLine()) != null){
					Pattern p = Pattern.compile(".*(\\d{2}/\\w{3}/\\d{4}:\\d{2}:\\d{2}:\\d{2}).*GET\\s/(.*)\\sHTTP");
					Matcher m = p.matcher(logEntry);
					if (m.find()){
						if (m.group(2).contains(KsamsokStatConstants.API)){
							storeAPIReq(m);
						} else {
							storeSOCHObjReq(m);
						}
					} else if (Logger.isTraceEnabled()){
						Logger.trace("Non interesting log entry: %s", logEntry);
					}
				}
			} catch (FileNotFoundException e) {
				Logger.error("Log file %s was not found", logFile.getAbsolutePath());
			} catch (IOException e) {
				Logger.error(e, "Problem reading from log-file %s", logFile.getAbsolutePath());
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						// Ignore
					}
				}
			}
	}

	private void storeSOCHObjReq(Matcher m) {
		String[] pathComp =m.group(2).split("/");
		if (pathComp.length == 4 || pathComp.length == 5){
			String institution = pathComp[1];
			String service = pathComp[2];
			String format;
			String instId;
			if (pathComp.length == 5){
				format = pathComp[3];
				instId = pathComp[4];
			} else {
				format = KsamsokStatConstants.FORMAT_RDF;
				instId = pathComp[3];
			}
			String datePattern = "dd/MMM/YYYY:HH:mm:ss";
			try {
				Date date = new SimpleDateFormat(datePattern).parse(m.group(1));
				new SOCHObject(date, institution, service, format, instId).save();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Logger.error("Too many path components in SOCH object request: %s", m.group(2));
		}

	}

	/**
	 * @param m
	 */
	private void storeAPIReq(Matcher m) {
		String datePattern = "dd/MMM/YYYY:HH:mm:ss";
		try {
			Date date = new SimpleDateFormat(datePattern).parse(m.group(1));
			String httpReq = m.group(2);
			String[] apiReq=httpReq.split("\\?");
			if (apiReq.length!=2){
				Logger.error("Erroneous api-request: %s", httpReq);
			} else {
				//Extract api parameters
				HashMap<String, String> methodReq = parseApiReq(apiReq[1]);
				String apiKey = methodReq.get(KsamsokStatConstants.API_KEY);
				String query = methodReq.get(KsamsokStatConstants.QUERY);
				String index = methodReq.get(KsamsokStatConstants.INDEX);
				String uri = methodReq.get(KsamsokStatConstants.URI);
				String relation = methodReq.get(KsamsokStatConstants.RELATION);
				String value = methodReq.get(KsamsokStatConstants.VALUE);
				String recordSchema = methodReq.get(KsamsokStatConstants.RECORD_SCHEMA);
				String fields = methodReq.get(KsamsokStatConstants.FIELDS);
				String prefix = methodReq.get(KsamsokStatConstants.PREFIX);
				String words = methodReq.get(KsamsokStatConstants.WORDS);
				//Store to db
				Logger.info("METHOD: %s", methodReq.get(KsamsokStatConstants.METHOD));
				switch (methodReq.get(KsamsokStatConstants.METHOD)){
				case KsamsokStatConstants.ALL_INDEX_UNIQUE_VALUE_COUNT :
					new AllIndexUniqueValueCount(date, apiKey, query, index).save();
					break;
				case KsamsokStatConstants.FACET :
					new Facet(date, apiKey, query, index).save();
					break;
				case KsamsokStatConstants.GET_GEO_RESOURCE : 
					new GetGeoResource(date, apiKey, uri).save();
					break;
				case KsamsokStatConstants.GET_RELATION_TYPES :
					new GetRelationTypes(date, apiKey, relation).save();
					break;
				case KsamsokStatConstants.GET_RELATIONS :
					new GetRelations(date, apiKey, relation, uri).save();
					break;
				case KsamsokStatConstants.GET_SERVICE_ORGANIZATION :
					new GetServiceOrganization(date, apiKey, value).save();
					break;
				case KsamsokStatConstants.RSS :
					new Rss(date, apiKey, query, recordSchema, fields).save();
					break;
				case KsamsokStatConstants.SEARCH :
					new Search(date, apiKey, query, recordSchema, fields).save();
					break;
				case KsamsokStatConstants.SEARCH_HELP :
					new SearchHelp(date, apiKey, index, prefix).save();
					break;
				case KsamsokStatConstants.STATISTIC :
					new Statistic(date, apiKey, index).save();
					break;
				case KsamsokStatConstants.STATISTIC_SEARCH :
					new StatisticSearch(date, apiKey, query, index).save();
					break;
				case KsamsokStatConstants.STEM :
					new Stem(date, apiKey, words).save();
					break;
				default:
					Logger.error("Unknown api-method: %s", httpReq);
					break;
				}

			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private HashMap<String, String> parseApiReq(String apiReq) {
		
		HashMap<String, String> methodReq = new HashMap<String, String>();
		String[] methodCall=apiReq.split("&");
		if (methodCall.length<3){
			Logger.error("Too few arguments in api-method call: %s", apiReq);
		} else {
			for(int i = 0; i < methodCall.length; i++){
//				String[] keyValue = methodCall[i].split("=",1);
				String[] keyValue = methodCall[i].split("=");
				if (keyValue.length != 2){
					Logger.error("Missing value to key or key to value in api-request: %s", apiReq);
				} else {
					methodReq.put(keyValue[0], keyValue[1]);
				}
			}
		}
		return methodReq;
	}
}
