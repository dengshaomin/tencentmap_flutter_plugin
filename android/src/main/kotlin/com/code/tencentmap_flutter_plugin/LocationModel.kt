package com.code.tencentmap_flutter_plugin

import com.tencent.map.geolocation.TencentLocation

class LocationModel : java.io.Serializable {
    var name: String? = null
    var address: String? = null
    var latitude: Double? = 0.0
    var longitude: Double? = 0.0
    var cityCode: String? = null
    var nation: String? = null
    var province: String? = null
    var city: String? = null
    var district: String? = null
    var street: String? = null
    var streetNo: String? = null
    var town: String? = null

    companion object {
        fun transform(tencentLocation: TencentLocation?): LocationModel {
//            return mutableMapOf<String, Any?>().apply {
//                tencentLocation?.let {
//                    put("name", it.name)
//                    put("address", it.address)
//                    put("latitude", it.latitude)
//                    put("longitude", it.longitude)
//                    put("cityCode", it.cityCode)
//                    put("nation", it.nation)
//                    put("province", it.province)
//                    put("city", it.city)
//                    put("district", it.district)
//                    put("street", it.street)
//                    put("streetNo", it.streetNo)
//                    put("town", it.town)
//                }
//            }
            return LocationModel().apply {
                tencentLocation?.let {
                    name = it.name
                    address = it.address
                    latitude = it.latitude
                    longitude = it.longitude
                    cityCode = it.cityCode
                    nation = it.nation
                    province = it.province
                    city = it.city
                    district = it.district
                    street = it.street
                    streetNo = it.streetNo
                    town = it.town
                }
            }
        }
    }
}