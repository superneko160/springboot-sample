CREATE DATABASE IF NOT EXISTS test;
USE test;

CREATE TABLE IF NOT EXISTS products (
    id VARCHAR(10) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(100),
    price INT,
    stock INT,
    description TEXT,
    tags JSON,
    image_url VARCHAR(255)
) DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

INSERT INTO products (id, name, category, price, stock, description, tags, image_url) VALUES
('P001', 'ワイヤレスイヤホン', 'electronics', 12800, 150, 'ノイズキャンセリング機能搭載のワイヤレスイヤホン。最大30時間再生可能。', '["audio", "wireless", "noise-cancelling"]', 'https://example.com/images/P001.jpg'),
('P002', 'メカニカルキーボード', 'electronics', 9800, 80, '青軸採用のメカニカルキーボード。テンキーレスでコンパクト設計。', '["keyboard", "mechanical", "gaming"]', 'https://example.com/images/P002.jpg'),
('P003', 'スタンディングデスク', 'furniture', 45000, 30, '電動昇降式スタンディングデスク。天板サイズ140×70cm。', '["desk", "standing", "electric"]', 'https://example.com/images/P003.jpg'),
('P004', 'ステンレスボトル', 'daily_goods', 3200, 300, '真空二重構造のステンレスボトル。500ml。保冷・保温24時間対応。', '["bottle", "stainless", "eco"]', 'https://example.com/images/P004.jpg'),
('P005', 'ヨガマット', 'sports', 4500, 200, '厚さ6mmの滑り止め加工ヨガマット。収納ストラップ付き。', '["yoga", "fitness", "mat"]', 'https://example.com/images/P005.jpg'),
('P006', 'ポータブルモニター', 'electronics', 28000, 50, '15.6インチのUSB-C接続ポータブルモニター。フルHD対応。', '["monitor", "portable", "usb-c"]', 'https://example.com/images/P006.jpg'),
('P007', 'アロマディフューザー', 'daily_goods', 5800, 120, '超音波式アロマディフューザー。タイマー機能付き。静音設計。', '["aroma", "relax", "humidifier"]', 'https://example.com/images/P007.jpg'),
('P008', 'プロテインシェイカー', 'sports', 1500, 400, 'BPAフリーのプロテインシェイカー。600ml。目盛り付き。', '["shaker", "fitness", "protein"]', 'https://example.com/images/P008.jpg');
