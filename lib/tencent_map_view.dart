import 'package:flutter/foundation.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';
import 'package:flutter/rendering.dart';
import 'package:flutter/services.dart';
import 'package:sliding_up_panel/sliding_up_panel.dart';

class TencentMapView extends StatefulWidget {
  const TencentMapView({Key? key}) : super(key: key);
  static const String TententViewType = "view-type-tencent-map";
  static const int TententViewId = 999;

  @override
  State<StatefulWidget> createState() {
    // TODO: implement createState
    return _TencentMapViewState();
  }
}

class _TencentMapViewState extends State<TencentMapView> {
  @override
  Widget build(BuildContext context) {
    return PlatformViewLink(
      viewType: TencentMapView.TententViewType,
      surfaceFactory: (context, controller) {
        return AndroidViewSurface(
          controller: controller as AndroidViewController,
          gestureRecognizers: const <Factory<OneSequenceGestureRecognizer>>{},
          hitTestBehavior: PlatformViewHitTestBehavior.opaque,
        );
      },
      onCreatePlatformView: (params) {
        return PlatformViewsService.initExpensiveAndroidView(
          id: TencentMapView.TententViewId,
          viewType: TencentMapView.TententViewType,
          layoutDirection: TextDirection.ltr,
          creationParams: <String, dynamic>{},
          creationParamsCodec: const StandardMessageCodec(),
          onFocus: () {
            params.onFocusChanged(true);
          },
        )
          ..addOnPlatformViewCreatedListener(params.onPlatformViewCreated)
          ..create();
      },
    );
  }

  void _startLocation() {}
}
