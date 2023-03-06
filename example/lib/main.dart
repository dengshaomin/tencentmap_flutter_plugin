import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';
import 'package:sliding_up_panel/sliding_up_panel.dart';
import 'package:tencentmap_flutter_plugin/tencent_map_view.dart';
import 'package:tencentmap_flutter_plugin/tencentmap_flutter_plugin_method_channel.dart';
import 'package:tencentmap_flutter_plugin_example/MapType.dart';

typedef MapTypeCallBack = Function(int type);

void main() {
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  final _tencentFlutterPlugin = TencentmapFlutterPlugin();

  @override
  void initState() {
    super.initState();
    _tencentFlutterPlugin.addObserver(MethodMap.startSingleLocation,
        (p1) async {
      _tencentFlutterPlugin.animateCamera(p1 as Map<String, Object?>);
    });
  }

  Future<void> singleLocation() async {
    try {
      _tencentFlutterPlugin.startSingleLocation();
    } on PlatformException {}
  }

  Future<void> setMapType(int type) async {
    try {
      _tencentFlutterPlugin.setMapType(type);
    } on PlatformException {}
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return MaterialApp(
      title: "tencent_map",
      home:
      Stack(
        children: [
          const TencentMapView(),
          SlidingUpPanel(
            padding: const EdgeInsets.all(10),
            panel: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                Container(
                  decoration: BoxDecoration(
                    borderRadius: BorderRadius.circular(5),
                    border: Border.all(color: Colors.grey, width: 1),
                  ),
                  child: Column(
                    children: [
                      MapTypeGroup((type) => setMapType(type)),
                    ],
                  ),
                ),
                TextButton(
                    style: ButtonStyle(backgroundColor: MaterialStateProperty.all(Colors.green[100])),
                    onPressed: singleLocation,
                    child: const Text("开始定位"))
              ],
            ),
          ),
        ],
      ),
    );
  }
}

class MapTypeGroup extends StatefulWidget {
  final MapTypeCallBack mapTypeCallBack;

  MapTypeGroup(this.mapTypeCallBack);

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return MapTypeState();
  }
}

class MapTypeState extends State<MapTypeGroup> {
  int mapType = MapType.MAP_TYPE_NORMAL;

  void _changeType(int type) {
    widget.mapTypeCallBack.call(type);
    setState(() {
      mapType = type;
    });
  }

  @override
  Widget build(BuildContext context) {
    // TODO: implement build
    return Column(crossAxisAlignment: CrossAxisAlignment.start, children: [
      const Text(
        "地图类型",
        style: TextStyle(fontSize: 16),
      ),
      Row(
        mainAxisAlignment: MainAxisAlignment.spaceBetween,
        children: [
          TextButton(
              onPressed: () => _changeType(MapType.MAP_TYPE_NORMAL),
              child: Row(
                children: [
                  Icon(
                    mapType == MapType.MAP_TYPE_NORMAL
                        ? Icons.radio_button_checked
                        : Icons.radio_button_unchecked,
                    size: 18,
                  ),
                  const Text("NORMAL"),
                ],
              )),
          TextButton(
              onPressed: () => _changeType(MapType.MAP_TYPE_SATELLITE),
              child: Row(
                children: [
                  Icon(
                    mapType == MapType.MAP_TYPE_SATELLITE
                        ? Icons.radio_button_checked
                        : Icons.radio_button_unchecked,
                    size: 18,
                  ),
                  const Text("SATELLITE"),
                ],
              )),
          TextButton(
              onPressed: () => _changeType(MapType.MAP_TYPE_DARK),
              child: Row(
                children: [
                  Icon(
                    mapType == MapType.MAP_TYPE_DARK
                        ? Icons.radio_button_checked
                        : Icons.radio_button_unchecked,
                    size: 18,
                  ),
                  const Text("DARK"),
                ],
              ))
        ],
      )
    ]);
  }
}
