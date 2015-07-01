package com.lambdaworks.redis;

import com.lambdaworks.redis.GeoArgs;
import com.lambdaworks.redis.GeoCoordinates;
import com.lambdaworks.redis.GeoWithin;
import com.lambdaworks.redis.GeoEncoded;
import java.util.List;
import java.util.Set;

/**
 * ${intent} for the Geo-API.
 * 
 * @author <a href="mailto:mpaluch@paluch.biz">Mark Paluch</a>
 * @since 4.0
 */
public interface RedisGeoCommands<K, V> {

    /**
     * Single geo add.
     * 
     * @param key the key of the geo set
     * @param longitude the longitude coordinate according to WGS84
     * @param latitude the latitude coordinate according to WGS84
     * @param member the member to add
     * @return Long integer-reply the number of elements that were added to the set
     */
    Long geoadd(K key, double longitude, double latitude, V member);

    /**
     * Multi geo add.
     * 
     * @param key the key of the geo set
     * @param lngLatMember triplets of double longitude, double latitude and V member
     * @return Long integer-reply the number of elements that were added to the set
     */
    Long geoadd(K key, Object... lngLatMember);

    /**
     * Retrieve members selected by distance with the center of {@code longitude} and {@code latitude}.
     * 
     * @param key the key of the geo set
     * @param longitude the longitude coordinate according to WGS84
     * @param latitude the latitude coordinate according to WGS84
     * @param distance radius distance
     * @param unit distance unit
     * @return bulk reply
     */
    Set<V> georadius(K key, double longitude, double latitude, double distance, GeoArgs.Unit unit);

    /**
     * Retrieve members selected by distance with the center of {@code longitude} and {@code latitude}.
     * 
     * @param key the key of the geo set
     * @param longitude the longitude coordinate according to WGS84
     * @param latitude the latitude coordinate according to WGS84
     * @param distance radius distance
     * @param unit distance unit
     * @param geoArgs args to control the result
     * @return nested multi-bulk reply. The {@link GeoWithin} contains only fields which were requested by {@link GeoArgs}
     */
    List<GeoWithin<V>> georadius(K key, double longitude, double latitude, double distance, GeoArgs.Unit unit, GeoArgs geoArgs);

    /**
     * Retrieve members selected by distance with the center of {@code member}. The member itself is always contained in the
     * results.
     * 
     * @param key the key of the geo set
     * @param member reference member
     * @param distance radius distance
     * @param unit distance unit
     * @return set of members
     */
    Set<V> georadiusbymember(K key, V member, double distance, GeoArgs.Unit unit);

    /**
     *
     * Retrieve members selected by distance with the center of {@code member}. The member itself is always contained in the
     * results.
     * 
     * @param key the key of the geo set
     * @param member reference member
     * @param distance radius distance
     * @param unit distance unit
     * @param geoArgs args to control the result
     * @return nested multi-bulk reply. The {@link GeoWithin} contains only fields which were requested by {@link GeoArgs}
     */
    List<GeoWithin<V>> georadiusbymember(K key, V member, double distance, GeoArgs.Unit unit, GeoArgs geoArgs);

    /**
     * Get geo coordinates for the {@code members}.
     *
     * @param key the key of the geo set
     * @param members the members
     *
     * @return a list of {@link GeoCoordinates}s representing the x,y position of each element specified in the arguments. For
     *         missing elements {@literal null} is returned.
     */
    List<GeoCoordinates> geopos(K key, V... members);

    /**
     *
     * Retrieve distance between points {@code from} and {@code to}. If one or more elements are missing {@literal null} is
     * returned. Default in meters by, otherwise according to {@code unit}
     *
     * @param key the key of the geo set
     * @param from from member
     * @param to to member
     * @param unit distance unit
     *
     * @return distance between points {@code from} and {@code to}. If one or more elements are missing {@literal null} is
     *         returned.
     */
    Double geodist(K key, V from, V to, GeoArgs.Unit unit);

    /**
     *
     * Encode {@code longitude} and {@code latitude} to highest geohash accuracy.
     *
     * @param longitude the longitude coordinate according to WGS84
     * @param latitude the latitude coordinate according to WGS84
     * @return multi-bulk reply with 4 elements 1: the 52-bit geohash integer for your longitude/latitude, 2: The minimum corner
     *         of your geohash {@link GeoCoordinates}, 3: The maximum corner of your geohash {@link GeoCoordinates}, 4: The
     *         averaged center of your geohash {@link GeoCoordinates}.
     */
    GeoEncoded geoencode(double longitude, double latitude);

    /**
     *
     * Encode {@code longitude} and {@code latitude} to highest geohash accuracy.
     *
     * @param longitude the longitude coordinate according to WGS84
     * @param latitude the latitude coordinate according to WGS84
     * @param distance distance for geohash accuracy
     * @param unit the distance unit
     * @return multi-bulk reply with four components 1: the 52-bit geohash integer for your longitude/latitude, 2: The minimum
     *         corner of your geohash {@link GeoCoordinates}, 3: The maximum corner of your geohash {@link GeoCoordinates}, 4:
     *         The averaged center of your geohash {@link GeoCoordinates}.
     */
    GeoEncoded geoencode(double longitude, double latitude, double distance, GeoArgs.Unit unit);

    /**
     *
     * Decode geohash.
     *
     * @param geohash geohash containing your longitude/latitude
     * @return a list of {@link GeoCoordinates}s (nested multi-bulk) with 3 elements 1: minimum decoded corner, 2: maximum
     *         decoded corner, 3: averaged center of bounding box.
     */
    GeoEncoded geodecode(long geohash);
}
