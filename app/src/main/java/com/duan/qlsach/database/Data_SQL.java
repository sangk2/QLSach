package com.duan.qlsach.database;

public class Data_SQL {
    public static final String insert_TaiKhoan =
            "insert into TaiKhoan (tenDangNhap, tenNguoiDung, matKhau) values" +
                    "('admin','Tấn Sang','123')," +
                    "('sang', 'Sang Tấn', '123')";

    public static final String insert_KeSach =
            "insert into KeSach (tenKS) values" +
                    "('Kệ Sách A')," +
                    "('Kệ Sách B')," +
                    "('Kệ Sách C')";

    public static final String insert_TheLoai =
            "insert into TheLoai (tenLoai) values" +
                    "('Sách văn học')," +
                    "('Từ điển')," +
                    "('Sách thiếu nhi')," +
                    "('Truyện tranh')," +
                    "('Giáo trình')";

    public static final String insert_Sach =
            "insert into Sach (tenSach, tacGia, NXB, theLoai, soTrang, gia, ngayNhap, keSach, tinhTrang, tomTat) values" +
                    "('Doraemon','Fujiko Fujio','NXB Kim Đồng','4','392','15000','2021/11/7','1','Sách mới','Các câu chuyện trong Doraemon thường có một" +
                    " công thức chung,đó là xoay quanh những rắc rối hay xảy ra với cậu bé Nobita học lớp bốn, nhân vật chính thứ nhì của bộ truyện. Doraemon" +
                    " có một chiếc túi thần kỳ trước bụng với đủ loại bảo bối của tương lai. Cốt truyện thường gặp nhất sẽ là Nobita trở về nhà khóc lóc với" +
                    " những rắc rối mà cậu gặp phải ở trường hoặc với bạn bè. Sau khi bị cậu bé van nài hoặc thúc giục, Doraemon sẽ đưa ra một bảo bối giúp" +
                    " Nobita giải quyết những rắc rối của mình, hoặc là để trả đũa hay khoe khoang với bạn bè của cậu. Nobita sẽ lại thường đi quá xa so với" +
                    " dự định ban đầu của Doraemon, thậm chí với những bảo bối mới cậu còn gặp rắc rối lớn hơn trước đó. Đôi khi những người bạn của Nobita, " +
                    "thường là Suneo (Xêkô) hoặc Jaian (Chaien) lại lấy trộm những bảo bối và sử dụng chúng không đúng mục đích. Tuy nhiên thường thì ở cuối mỗi" +
                    " câu chuyện, những ai sử dụng sai mục đích bảo bối sẽ phải chịu hậu quả do mình gây ra, và người đọc sẽ rút ra được bài học từ đó.')," +
                    "('Từ điển Anh-Việt','Nhà sách Minh Thắng','NXB Hồng Đức','2','1296','120000','2021/11/8','1','Sách cũ','Nhằm đáp ứng nhu cầu học tiếng anh" +
                    " đa dạng của độc giả , chúng tôi đã biên soạn bộ từ điển Anh - Việt để phục vụ cho người học tiếng anh thuộc mọi trình độ.')," +
                    "('Truyện cổ tích Việt Nam','Nhà sách Minh Thắng','NXB Mỹ Thuật','3','562','150000','2021/11/9','2','Sách tái bản','Những câu truyện đã nuôi" +
                    " dưỡng bồi đắp tâm hồn, phát huy trí tưởng tượng của biết bao thế hệ thiếu nhi Việt Nam.')";

    public static final String insert_MuonTra =
            "insert into MuonTra (maSach, nguoiMuon, batDau, hanTra, tinhTrang, ghiChu) values" +
                    "('3','An','2021/11/5','2021/11/9','Sách tái bản','Sách rất hay')";
}
