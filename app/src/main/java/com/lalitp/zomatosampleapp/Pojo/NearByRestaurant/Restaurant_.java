
package com.lalitp.zomatosampleapp.Pojo.NearByRestaurant;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant_ {

    @SerializedName("R")
    @Expose
    private R_Data r;
    @SerializedName("apikey")
    @Expose
    private String apikey;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("location")
    @Expose
    private Location location;
    @SerializedName("switch_to_order_menu")
    @Expose
    private String switchToOrderMenu;
    @SerializedName("cuisines")
    @Expose
    private String cuisines;
    @SerializedName("average_cost_for_two")
    @Expose
    private String averageCostForTwo;
    @SerializedName("price_range")
    @Expose
    private String priceRange;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("offers")
    @Expose
    private List<Object> offers = null;
    @SerializedName("zomato_events")
    @Expose
    private List<ZomatoEvent> zomatoEvents = null;
    @SerializedName("opentable_support")
    @Expose
    private String opentableSupport;
    @SerializedName("is_zomato_book_res")
    @Expose
    private String isZomatoBookRes;
    @SerializedName("mezzo_provider")
    @Expose
    private String mezzoProvider;
    @SerializedName("is_book_form_web_view")
    @Expose
    private String isBookFormWebView;
    @SerializedName("book_form_web_view_url")
    @Expose
    private String bookFormWebViewUrl;
    @SerializedName("book_again_url")
    @Expose
    private String bookAgainUrl;
    @SerializedName("thumb")
    @Expose
    private String thumb;
    @SerializedName("user_rating")
    @Expose
    private UserRating userRating;
    @SerializedName("photos_url")
    @Expose
    private String photosUrl;
    @SerializedName("menu_url")
    @Expose
    private String menuUrl;
    @SerializedName("featured_image")
    @Expose
    private String featuredImage;
    @SerializedName("medio_provider")
    @Expose
    private String medioProvider;
    @SerializedName("has_online_delivery")
    @Expose
    private String hasOnlineDelivery;
    @SerializedName("is_delivering_now")
    @Expose
    private String isDeliveringNow;
    @SerializedName("include_bogo_offers")
    @Expose
    private Boolean includeBogoOffers;
    @SerializedName("deeplink")
    @Expose
    private String deeplink;
    @SerializedName("is_table_reservation_supported")
    @Expose
    private String isTableReservationSupported;
    @SerializedName("has_table_booking")
    @Expose
    private String hasTableBooking;
    @SerializedName("events_url")
    @Expose
    private String eventsUrl;
    @SerializedName("establishment_types")
    @Expose
    private List<Object> establishmentTypes = null;

    public R_Data getR() {
        return r;
    }

    public void setR(R_Data r) {
        this.r = r;
    }

    public String getApikey() {
        return apikey;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getSwitchToOrderMenu() {
        return switchToOrderMenu;
    }

    public void setSwitchToOrderMenu(String switchToOrderMenu) {
        this.switchToOrderMenu = switchToOrderMenu;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    public String getAverageCostForTwo() {
        return averageCostForTwo;
    }

    public void setAverageCostForTwo(String averageCostForTwo) {
        this.averageCostForTwo = averageCostForTwo;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<Object> getOffers() {
        return offers;
    }

    public void setOffers(List<Object> offers) {
        this.offers = offers;
    }

    public List<ZomatoEvent> getZomatoEvents() {
        return zomatoEvents;
    }

    public void setZomatoEvents(List<ZomatoEvent> zomatoEvents) {
        this.zomatoEvents = zomatoEvents;
    }

    public String getOpentableSupport() {
        return opentableSupport;
    }

    public void setOpentableSupport(String opentableSupport) {
        this.opentableSupport = opentableSupport;
    }

    public String getIsZomatoBookRes() {
        return isZomatoBookRes;
    }

    public void setIsZomatoBookRes(String isZomatoBookRes) {
        this.isZomatoBookRes = isZomatoBookRes;
    }

    public String getMezzoProvider() {
        return mezzoProvider;
    }

    public void setMezzoProvider(String mezzoProvider) {
        this.mezzoProvider = mezzoProvider;
    }

    public String getIsBookFormWebView() {
        return isBookFormWebView;
    }

    public void setIsBookFormWebView(String isBookFormWebView) {
        this.isBookFormWebView = isBookFormWebView;
    }

    public String getBookFormWebViewUrl() {
        return bookFormWebViewUrl;
    }

    public void setBookFormWebViewUrl(String bookFormWebViewUrl) {
        this.bookFormWebViewUrl = bookFormWebViewUrl;
    }

    public String getBookAgainUrl() {
        return bookAgainUrl;
    }

    public void setBookAgainUrl(String bookAgainUrl) {
        this.bookAgainUrl = bookAgainUrl;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public UserRating getUserRating() {
        return userRating;
    }

    public void setUserRating(UserRating userRating) {
        this.userRating = userRating;
    }

    public String getPhotosUrl() {
        return photosUrl;
    }

    public void setPhotosUrl(String photosUrl) {
        this.photosUrl = photosUrl;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getMedioProvider() {
        return medioProvider;
    }

    public void setMedioProvider(String medioProvider) {
        this.medioProvider = medioProvider;
    }

    public String getHasOnlineDelivery() {
        return hasOnlineDelivery;
    }

    public void setHasOnlineDelivery(String hasOnlineDelivery) {
        this.hasOnlineDelivery = hasOnlineDelivery;
    }

    public String getIsDeliveringNow() {
        return isDeliveringNow;
    }

    public void setIsDeliveringNow(String isDeliveringNow) {
        this.isDeliveringNow = isDeliveringNow;
    }

    public Boolean getIncludeBogoOffers() {
        return includeBogoOffers;
    }

    public void setIncludeBogoOffers(Boolean includeBogoOffers) {
        this.includeBogoOffers = includeBogoOffers;
    }

    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    public String getIsTableReservationSupported() {
        return isTableReservationSupported;
    }

    public void setIsTableReservationSupported(String isTableReservationSupported) {
        this.isTableReservationSupported = isTableReservationSupported;
    }

    public String getHasTableBooking() {
        return hasTableBooking;
    }

    public void setHasTableBooking(String hasTableBooking) {
        this.hasTableBooking = hasTableBooking;
    }

    public String getEventsUrl() {
        return eventsUrl;
    }

    public void setEventsUrl(String eventsUrl) {
        this.eventsUrl = eventsUrl;
    }

    public List<Object> getEstablishmentTypes() {
        return establishmentTypes;
    }

    public void setEstablishmentTypes(List<Object> establishmentTypes) {
        this.establishmentTypes = establishmentTypes;
    }
}
