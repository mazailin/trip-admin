<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>行程轨迹</title>
	<meta name="decorator" content="default"/>
    <style>
        html, body {
            height: 100%;
            margin: 0;
            padding: 0;
        }
        #map {
            height: 100%;
        }
    </style>
</head>
<body>
<div id="map"></div>
<script type="text/javascript">

    var poly;
    var map;

    var route = [
        <c:forEach items="${route}" var="map" varStatus="s">
            <c:if test="${!s.last}">
            {
                lng: ${map.lng},
                lat: ${map.lat},
                timing: '<fmt:formatDate value="${map.timing}" pattern="yyyy-MM-dd HH:mm:ss"/>'
            },
            </c:if>
            <c:if test="${s.last}">
            {
                lng: ${map.lng},
                lat: ${map.lat},
                timing: '<fmt:formatDate value="${map.timing}" pattern="yyyy-MM-dd HH:mm:ss"/>'
            }
            </c:if>
        </c:forEach>
    ];

    function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {
            zoom: 20,
            center: route[0] || {lat:39.916304,lng:116.397112}
        });

        var lineSymbol = {
            path: google.maps.SymbolPath.CIRCLE,
            scale: 8,
            strokeColor: '#393'
        };

        if (route && route.length > 0) {
            poly = new google.maps.Polyline({
                path: route,
                strokeColor: '#FF00FF',
                strokeOpacity: 1.0,
                strokeWeight: 3,
                icons: [{
                    icon: lineSymbol,
                    offset: '100%'
                }]
            });
            poly.setMap(map);
        }

        var image = {
            url: '${ctxStatic}/images/beachflag.png',
            size: new google.maps.Size(20, 32),
            origin: new google.maps.Point(0, 0),
            anchor: new google.maps.Point(0, 32)
        };
        var shape = {
            coords: [1, 1, 1, 20, 18, 20, 18, 1],
            type: 'poly'
        };

        route.forEach(function(position){
            var marker = new google.maps.Marker({
                position: position,
                icon: image,
                shape: shape,
                animation: google.maps.Animation.DROP,
                map: map
            });

            attachSecretMessage(marker, '纬度:' + position.lat +
                    '<br/>' + '经度:' + position.lng +
                    '<br/>' + '时间:' + position.timing
            );

        });

        animateCircle(poly);
    }

    function attachSecretMessage(marker, secretMessage) {
        var infowindow = new google.maps.InfoWindow({
            content: secretMessage
        });

        marker.addListener('click', function() {
            infowindow.open(marker.get('map'), marker);
        });
    }

    function addLatLng(position) {
        var path = poly.getPath();

        path.push(new google.maps.LatLng(position.lat,position.lng));

        var image = {
            url: '${ctxStatic}/images/beachflag.png',
            size: new google.maps.Size(20, 32),
            origin: new google.maps.Point(0, 0),
            anchor: new google.maps.Point(0, 32)
        };
        var shape = {
            coords: [1, 1, 1, 20, 18, 20, 18, 1],
            type: 'poly'
        };

        var marker = new google.maps.Marker({
            position: position,
            icon: image,
            shape: shape,
            animation: google.maps.Animation.DROP,
            map: map
        });

        attachSecretMessage(marker, '纬度:' + position.lat +
                '<br/>' + '经度:' + position.lng +
                '<br/>' + '时间:' + position.timing
        );
    }

    function animateCircle(line) {
        if (!line) return;
        var count = 0;
        window.setInterval(function() {
            count = (count + 1) % 200;

            var icons = line.get('icons');
            icons[0].offset = (count / 2) + '%';
            line.set('icons', icons);
        }, 100);
    }

    window.setInterval(function() {
        var timing = "",
            lat = 0,
            lng = 0;
        if (route && route.length > 0) {
            var position = route[route.length - 1];
            timing = position.timing;
            lat = position.lat;
            lng = position.lng;
        }
        $.ajax({
            url: '${ctx}/tms/position/refresh',
            data: {
                "userId": "${position.userId}",
                "lat": lat,
                "lng": lng,
                "timing": timing
            },
            dataType: 'json'
        }).done(function (result) {
            result.forEach(function(position){
                addLatLng(position);
                route.push(position);
            });
        });
    }, 30000);


</script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAObFcwI1lG4WjSFQkEsSz09d5nMlyrhj8&signed_in=false&callback=initMap"></script>
</body>
</html>