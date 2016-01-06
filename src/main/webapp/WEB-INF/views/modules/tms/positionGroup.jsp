<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>团队实时位置</title>
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
    var markers = [];

    var points = [
        <c:forEach items="${points}" var="map" varStatus="s">
            <c:if test="${!s.last}">
            {
                lng: ${map.lng},
                lat: ${map.lat},
                name: '${map.name}'
            },
            </c:if>
            <c:if test="${s.last}">
            {
                lng: ${map.lng},
                lat: ${map.lat},
                name: '${map.name}'
            }
            </c:if>
        </c:forEach>
    ];

    function initMap() {
        map = new google.maps.Map(document.getElementById('map'), {
            zoom: 20,
            center: points[0] || {lat:39.916304,lng:116.397112}
        });

        drop();
    }

    function attachSecretMessage(marker, secretMessage) {
        var infowindow = new google.maps.InfoWindow({
            content: secretMessage
        });

        marker.addListener('click', function() {
            infowindow.open(marker.get('map'), marker);
        });
    }

    function drop() {
        clearMarkers();
        points.forEach(function(position){
            var marker = new google.maps.Marker({
                position: position,
                animation: google.maps.Animation.DROP,
                map: map
            });

            markers.push(marker);

            attachSecretMessage(marker, '纬度:' + position['lat'] +
                    '<br/>' + '经度:' + position['lng'] +
                    '<br/>' + '用户:' + position['name']
            );

        });
    }

    function clearMarkers() {
        for (var i = 0; i < markers.length; i++) {
            markers[i].setMap(null);
        }
        markers = [];
    }

    window.setInterval(function() {
        $.ajax({
            url: '${ctx}/tms/position/group/refresh',
            data: {
                "group": "${group}"
            },
            dataType: 'json'
        }).done(function (result) {
            points = [];
            result.forEach(function(position){
                points.push(position);
            });
            drop();
        });
    }, 30000);


</script>
<script async defer
        src="https://maps.googleapis.com/maps/api/js?key=AIzaSyAObFcwI1lG4WjSFQkEsSz09d5nMlyrhj8&signed_in=false&callback=initMap"></script>
</body>
</html>