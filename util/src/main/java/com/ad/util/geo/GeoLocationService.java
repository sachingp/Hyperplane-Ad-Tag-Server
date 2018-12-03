package com.ad.util.geo;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

/**
 * @author sagupta
 */

@Slf4j
public class GeoLocationService {

  private static GeoLocationService geoLocationService;

  private DatabaseReader reader = null;

  /**

   *

   * @param geoDataFile

   * @throws Exception

   */

  private GeoLocationService(String geoDataFile) throws Exception {

    log.debug("Initializing Geo look up service with geo: {} ", geoDataFile);

    try {

      File database = new File(geoDataFile);

      reader = new DatabaseReader.Builder(database).build();


    } catch (final IOException e) {

      throw new Exception(e);

    }

  }

  /**

   *

   * @param geoDataFile

   * @throws Exception

   */

  public static void init(String geoDataFile) throws Exception {

    if (geoLocationService == null) {

      // This method should be called from Service Initializer based on

      // the service type and to be called on start-up

      log.info("Initializing the Geo Lookup Service.");

      geoLocationService = new GeoLocationService(geoDataFile);

    }

  }


  public static void destroy() throws IOException {

    if (geoLocationService == null) {

      return;

    }

    geoLocationService.reader.close();

    geoLocationService = null;

  }

  /**
   * @return city response
   */


  public static CityResponse getLocationForIp(final String ip)

      throws IOException, GeoIp2Exception {

    InetAddress ipAddress = InetAddress.getByName(ip);

    return geoLocationService.reader.city(ipAddress);

  }

  /**
   * @return CityResponse
   */


  public static CityResponse getLocationForIpIfExists(final String ip) {

    try {

      InetAddress ipAddress = InetAddress.getByName(ip);

      return geoLocationService.reader.city(ipAddress);

    } catch (final IOException e) {

      log.error("IP not found. Value: " + ip);

      return null;

    } catch (final GeoIp2Exception e) {

      log.error("IP not found. Value: {}", ip);

      return null;

    } catch (final Exception e) {

      log.error("IP not found. Value: {}", ip);

      return null;

    }

  }

}
