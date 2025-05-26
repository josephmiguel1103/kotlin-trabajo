// lib/data/models/poi_model.dart
import 'package:json_annotation/json_annotation.dart';

part 'poi_model.g.dart';

@JsonSerializable()
class PoiModel {
  final int id;
  final String nombre;
  final String descripcion;
  final String imagenUrl;

  PoiModel({
    required this.id,
    required this.nombre,
    required this.descripcion,
    required this.imagenUrl,
  });

  factory PoiModel.fromJson(Map<String, dynamic> json) =>
      _$PoiModelFromJson(json);
  Map<String, dynamic> toJson() => _$PoiModelToJson(this);
}
