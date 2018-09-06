<?php
$keKategori = (htmlspecialchars($_GET['keKategori']));
$keIsbn = (htmlspecialchars($_GET['keIsbn']));
$keKitapAdi = (htmlspecialchars($_GET['keKitapAdi']));
$keAciklama = (htmlspecialchars($_GET['keAciklama']));
$keYazar = (htmlspecialchars($_GET['keYazar']));
$keSayfa = (htmlspecialchars($_GET['keSayfa']));
$keYayinci = (htmlspecialchars($_GET['keYayinci']));
$keYayinlanmaTarih = (htmlspecialchars($_GET['keYayinlanmaTarih']));
$keDil = (htmlspecialchars($_GET['keDil']));
$keTelefon = (htmlspecialchars($_GET['keTelefon']));
$keIl = (htmlspecialchars($_GET['keIl']));
$keIlce = (htmlspecialchars($_GET['keIlce']));
$keFiyat = (htmlspecialchars($_GET['keFiyat']));
$keUyeId = (htmlspecialchars($_GET['keUyeId']));
$keResim = (htmlspecialchars($_GET['keResim']));


$database = 'samet_feyyaz';
$host = '95.211.242.157';
$dbuser = 'feyyaz';
$dbpass = 'feyyaz2015';
$conn = new mysqli($host, $dbuser, $dbpass, $database);
mysqli_set_charset($conn,'utf8mb4');

$ke_ilan_tarihi = date("d.m.Y");

$sorgu_cumle = "insert into kitap_table(kitap_isbn13 , kitap_adi , kitap_aciklama , kitap_yazar , kitap_sayfa , kitap_yayinci , ".
			   "kitap_tarih , kitap_kategori , kitap_dil , kitap_resim , kitap_fiyat , kitap_uyeid , kitap_uyetelefon , kitap_il , ".
			   "kitap_ilce , kitap_ilantarih) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

$stmt = $conn->prepare($sorgu_cumle);
$stmt->bind_param("ssssississiissss", $keIsbn, $keKitapAdi, $keAciklama , $keYazar, $keSayfa, $keYayinci , $keYayinlanmaTarih, $keKategori, $keDil , $keResim, $keFiyat, $keUyeId , $keTelefon, $keIl, $keIlce, $ke_ilan_tarihi );
$stmt->execute();

if($stmt){
echo '{"durum":1}';
}
else if(!$stmt){
echo '{"durum":0}';
}

?>

