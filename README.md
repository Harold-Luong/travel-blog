# travel-blog
# run dev: mvn spring-boot:run -Dspring-boot.run.profiles=dev

Mô tả chức năng:
- user có thể tạo nhiều chuyến hành trình (trips itinerary)
- mỗi chuyến hành trình có nhiều ngày đi
- mỗi ngày có thể đi nhiều nơi (nhiều location)
- trong mỗi trip có nhiều locations (location dùng để hiển thị trên map)
- trip, location có slug tự động tạo dựa trên title, không trùng có định dạng  như (xin-chao-viet-nam, xin-chao-viet-nam-1 )
- dựa trên slug đó để hiện trên url khi xem bài viết
