// lib/api_service.dart
import 'dart:convert';
import 'package:http/http.dart' as http;
import 'item.dart';

class ApiService {
  // Ajusta el puerto si tu servidor corre en otro
  static const _baseUrl = 'http://localhost:3000';

  Future<List<Item>> fetchItems() async {
    final response = await http.get(Uri.parse('$_baseUrl/items'));
    if (response.statusCode == 200) {
      final List data = jsonDecode(response.body) as List;
      return data.map((json) => Item.fromJson(json)).toList();
    } else {
      throw Exception('Error cargando items: ${response.statusCode}');
    }
  }
}
