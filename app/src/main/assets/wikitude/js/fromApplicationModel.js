// implementation of AR-Experience (aka "World")
var World = {

    //  user's latest known location, accessible via userLocation.latitude, userLocation.longitude, userLocation.altitude
    userLocation: null,
    // different POI-Marker assets
    markerDrawable_idle: null,
    markerDrawable_selected: null,
    markerDrawable_directionIndicator: null,

    // list of AR.GeoObjects that are currently shown in the scene / World
    markerList: [],

    // The last selected marker
    currentMarker: null,

    // called to inject new POI data
    loadPoisFromJsonData: function loadPoisFromJsonDataFn(poiData) {

        // destroys all existing AR-Objects (markers & radar)
        AR.context.destroyAll();

        // show radar & set click-listener
        PoiRadar.show();
        $('#radarContainer').unbind('click');
        $("#radarContainer").click(PoiRadar.clickedRadar);

        // empty list of visible markers
        World.markerList = [];

        // start loading marker assets
        World.markerDrawable_idle = new AR.ImageResource("assets/marker_idle.png");
        World.markerDrawable_selected = new AR.ImageResource("assets/marker_selected.png");
        World.markerDrawable_directionIndicator = new AR.ImageResource("assets/indi.png");

        // loop through POI-information and create an AR.GeoObject (=Marker) per POI
        for (var currentPlaceNr = 0; currentPlaceNr < poiData.length; currentPlaceNr++) {
            //  Vary the the latitude and longitude
            var locationVariance = .003;
            var lat = parseFloat(World.userLocation['latitude']) + ((Math.random() * locationVariance) - locationVariance / 2);
            var long = parseFloat(World.userLocation['longitude']) + ((Math.random() * locationVariance) - locationVariance / 2);

            var singlePoi = {
                "id": poiData[currentPlaceNr].id,
                "latitude": lat,
                "longitude": long,
                "altitude": parseFloat(poiData[currentPlaceNr].altitude),
                "title": poiData[currentPlaceNr].name,
                "description": poiData[currentPlaceNr].description
            };

            World.markerList.push(new Marker(singlePoi));
        }

        World.updateStatusMessage(currentPlaceNr + ' places loaded');

        // set distance slider to 100%
        $("#panel-distance-range").val(100);
        $("#panel-distance-range").slider("refresh");
    },

    // updates status message shon in small "i"-button aligned bottom center
    updateStatusMessage: function updateStatusMessageFn(message, isWarning) {

        var themeToUse = isWarning ? "e" : "c";
        var iconToUse = isWarning ? "alert" : "info";

        $("#status-message").html(message);
        $("#popupInfoButton").buttonMarkup({
            theme: themeToUse
        });
        $("#popupInfoButton").buttonMarkup({
            icon: iconToUse
        });
    },

    // location updates, fired every time you call architectView.setHighlightedLocation() in native environment
    locationChanged: function locationChangedFn(lat, lon, alt, acc) {

        // store user's current location in World.userLocation, so you always know where user is
        World.userLocation = {
            'latitude': lat,
            'longitude': lon,
            'altitude': alt,
            'accuracy': acc
        };
    },

    // fired when user pressed maker in cam
    onMarkerSelected: function onMarkerSelectedFn(marker) {

        // deselect previous marker
        if (World.currentMarker) {
            if (World.currentMarker.poiData.id == marker.poiData.id) {
                return;
            }
            World.currentMarker.setDeselected(World.currentMarker);
        }

        // highlight current one
        marker.setSelected(marker);
        World.currentMarker = marker;

        // update panel values
        $("#poi-detail-title").html(marker.poiData.title);
        $("#poi-detail-description").html(marker.poiData.description);

        var distanceToUserValue = (marker.distanceToUser > 999) ? ((marker.distanceToUser / 1000).toFixed(2) + " km") : (Math.round(marker.distanceToUser) + " m");

        $("#poi-detail-distance").html(distanceToUserValue);

        // show panel
        $("#panel-poidetail").panel("open", 123);

        $( ".ui-panel-dismiss" ).unbind("mousedown");

        $("#panel-poidetail").on("panelbeforeclose", function(event, ui) {
            World.currentMarker.setDeselected(World.currentMarker);
        });
    },

    // screen was clicked but no geo-object was hit
    onScreenClick: function onScreenClickFn() {
        if (World.currentMarker) {
            World.currentMarker.setDeselected(World.currentMarker);
        }
    }

};

/* forward locationChanges to custom function */
AR.context.onLocationChanged = World.locationChanged;

/* forward clicks in empty area to World */
AR.context.onScreenClick = World.onScreenClick;