# 🌿 VineHopper

> **Plugin Hopper Siêu Mạnh cho Minecraft 1.21+ | Tương thích SuperiorSkyblock2**

[![Minecraft](https://img.shields.io/badge/Minecraft-1.21.11-brightgreen.svg)](https://www.minecraft.net/)
[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Paper](https://img.shields.io/badge/Paper-API-blue.svg)](https://papermc.io/)

---

## ✨ Tính Năng Nổi Bật

### 🚀 **Hopper Siêu Tốc**
- ⚡ **Tốc độ pickup**: 5 ticks (0.25s) - Nhanh gấp **1.6x** so với hopper vanilla
- 📦 **Tốc độ transfer**: 5 ticks (0.25s) - Hiệu suất vượt trội
- 🎯 **Phạm vi pickup**: Tự động nhặt vật phẩm trong **toàn bộ chunk** (16x16 blocks)

### 🎨 **Giao Diện Đẹp Mắt**
- 🌈 **Gradient Text**: Hỗ trợ màu gradient tuyệt đẹp
- 💎 **Hex Colors**: Màu sắc tùy chỉnh với mã hex (#RRGGBB)
- ✨ **Custom Display**: Tên và lore hopper có thể tùy chỉnh hoàn toàn

### 🔒 **Bảo Mật & Kiểm Soát**
- 🚫 **Chặn craft hopper**: Người chơi không thể craft hopper vanilla
- 🛡️ **Chống lách luật**: Không thể đổi tên hopper bằng anvil để bypass
- 👤 **Giới hạn cá nhân**: Mỗi người chơi có giới hạn số lượng hopper riêng
- 🔐 **Bảo vệ ownership**: Chỉ chủ sở hữu mới có thể xóa hopper của mình
- 🌍 **Chặn theo world**: Vô hiệu hóa hopper ở các world cụ thể

### 🏝️ **Tích Hợp SuperiorSkyblock2**
- ✅ **Tự động vô hiệu hóa** hopper limit của SuperiorSkyblock2
- ✅ **Chặn hopper vanilla** trong island, chỉ cho phép VineHopper
- ✅ **Không xung đột** với hệ thống upgrade của SS2

### 📊 **Hệ Thống Upgrade**
- 📈 **Nâng cấp phạm vi**: Tăng pickup-range từ 1 lên 4 chunk
- 💾 **Lưu trữ cá nhân**: Mỗi người chơi có upgrade riêng trong data.yml
- 🎮 **Console only**: Chỉ admin/console mới có quyền upgrade
- 🔄 **Tự động áp dụng**: Hopper mới đặt sẽ dùng upgrade của chủ sở hữu

### 🎮 **Quản Lý Dễ Dàng**
- 📋 **GUI Menu**: Shift+Click để mở menu quản lý hopper
- 🗑️ **Xóa an toàn**: Xóa hopper qua GUI, tự động trả về túi đồ
- 📊 **Hiển thị info**: Xem owner, vị trí, phạm vi pickup trong GUI
- 🔄 **Reload nhanh**: Tải lại config không cần restart server

### 🔌 **PlaceholderAPI Support**
- 📊 **%vh_pickup_range%**: Hiển thị phạm vi pickup của người chơi
- 📦 **%vh_hopper_count%**: Số lượng hopper đã đặt
- 🎯 **%vh_hopper_limit%**: Giới hạn hopper (hoặc ∞)
- 🎨 **Tích hợp DeluxeMenus**: Dùng placeholder trong menu tùy chỉnh

---

## 📦 Yêu Cầu

- **Minecraft**: 1.21.11+
- **Server**: Paper/Spigot
- **Java**: 21+
- **SuperiorSkyblock2** (Tùy chọn): 2024.3+
- **PlaceholderAPI** (Tùy chọn): 2.11.6+

---

## 🎯 Lệnh & Quyền

### Lệnh Cơ Bản
```
/vh                                    - Hiển thị trợ giúp
/vh reload                             - Tải lại plugin
/vh setlimit <player> <số>             - Đặt giới hạn hopper cho người chơi
/vh give <player> <amount>             - Tặng VineHopper cho người chơi
/vh upgrade pickup-range <player> <1-4> - Nâng cấp phạm vi (Console only)
```

### Quyền Hạn
```yaml
vinehopper.reload    - Quyền reload plugin
vinehopper.setlimit  - Quyền đặt giới hạn
vinehopper.give      - Quyền tặng hopper
```

---

## ⚙️ Cấu Hình

### 📄 **settings.yml**
```yaml
settings:
  player-limit: 3  # Giới hạn hopper mặc định

block-world:
  enable: true
  worlds:
    - world_nether
    - world_the_end

hopper:
  display_name: "<gradient:#FF6B6B:#4ECDC4>VineHopper</gradient>"
  lore:
    - "&#FFD700⚡ Tốc độ siêu nhanh"
    - "&#00FF00📦 Hút vật phẩm tự động"
  transfer_rate: 5   # Tốc độ chuyển (ticks)
  pickup_rate: 5     # Tốc độ nhặt (ticks)
  pickup_range: 1    # Phạm vi mặc định (chunk)
```

### 💾 **data.yml**
```yaml
players:
  <uuid>:
    name: "PlayerName"
    limit: 5
    hoppers: 3
    pickup-range: 2  # Upgrade cá nhân
    hopper-locations:
      - "world,100,64,200"

hopper-owners:
  "world,100,64,200": "<uuid>"  # Lưu owner của hopper
```

### 🎨 **gui.yml**
```yaml
gui:
  hopper-menu:
    title: "<gradient:#FF6B6B:#4ECDC4>Quản Lý Hopper</gradient>"
    size: 27
    items:
      info:
        slot: 11
        material: "PAPER"
        name: "&#FFD700Thông Tin Hopper"
        lore:
          - "&#FFFFFF"
          - "&#00FFFFVị trí: &#FFFFFF{x}, {y}, {z}"
          - "&#00FFFFWorld: &#FFFFFF{world}"
          - "&#00FFFFChủ sở hữu: &#FFFFFF{owner}"
          - "&#00FFFFPhạm vi pickup: &#FFFF00{pickup-range} chunk"
          - "&#FFFFFF"
```

---

## 🎨 Hệ Thống Màu Sắc

### Hex Color
```yaml
"&#FF6B6B Màu đỏ đẹp"
"&#4ECDC4 Màu xanh ngọc"
```

### Gradient
```yaml
"<gradient:#FF6B6B:#4ECDC4>Text gradient tuyệt đẹp</gradient>"
```

---

## 🔌 PlaceholderAPI

### Placeholders Có Sẵn
```
%vh_pickup_range%    - Phạm vi pickup của người chơi (1-4)
%vh_hopper_count%    - Số lượng hopper đã đặt
%vh_hopper_limit%    - Giới hạn hopper (hoặc ∞)
```

### Ví Dụ Sử Dụng
**Trong DeluxeMenus:**
```yaml
lore:
  - "Phạm vi: %vh_pickup_range% chunk"
  - "Hopper: %vh_hopper_count%/%vh_hopper_limit%"
```

**Kết quả:**
```
Phạm vi: 2 chunk
Hopper: 3/5
```

---

## 🔥 So Sánh Với Hopper Vanilla

| Tính Năng | Hopper Vanilla | VineHopper |
|-----------|----------------|------------|
| **Tốc độ pickup** | 8 ticks (0.4s) | 5 ticks (0.25s) ⚡ |
| **Phạm vi pickup** | 1 block | 1-4 chunk 🎯 |
| **Tự động nhặt** | ❌ | ✅ |
| **Upgrade được** | ❌ | ✅ |
| **Giới hạn cá nhân** | ❌ | ✅ |
| **GUI quản lý** | ❌ | ✅ |
| **Bảo vệ ownership** | ❌ | ✅ |
| **PlaceholderAPI** | ❌ | ✅ |

---

## 🛠️ Cài Đặt

1. **Tải file** `VineHopper.jar`
2. **Đặt vào** thư mục `plugins/`
3. **Khởi động** server
4. **Cấu hình** trong `plugins/VineHopper/`
5. **Reload** bằng `/vh reload`
6. **(Tùy chọn)** Cài PlaceholderAPI để dùng placeholder

---

## 🎮 Cách Sử Dụng

### Cho Người Chơi
1. Nhận VineHopper từ admin: `/vh give <tên> <số lượng>`
2. Đặt hopper như bình thường
3. Hopper tự động nhặt vật phẩm trong chunk
4. Shift+Click để mở menu quản lý
5. Xóa hopper qua GUI để lấy lại (chỉ chủ sở hữu)

### Cho Admin
1. Đặt giới hạn: `/vh setlimit <player> <số>`
2. Tặng hopper: `/vh give <player> <amount>`
3. Nâng cấp phạm vi (Console): `/vh upgrade pickup-range <player> <1-4>`
4. Cấu hình trong `settings.yml`
5. Reload: `/vh reload`

---

## 🌍 World Blocked

Ở các world bị chặn trong `block-world.worlds`:
- ✅ Hopper hoạt động như **hopper vanilla** bình thường
- ✅ Có thể đặt và phá tự do
- ❌ Không có tính năng tự động nhặt
- ❌ Không tính vào giới hạn

---

## 🏝️ SuperiorSkyblock2 Integration

- ✅ Tự động vô hiệu hóa hopper limit của island khi plugin khởi động
- ✅ Chặn đặt hopper vanilla trong island
- ✅ Chỉ cho phép VineHopper chính thức (kiểm tra display name + lore)
- ✅ Không ảnh hưởng đến các tính năng khác của SS2

---

## 📝 Lưu Ý

- ⚠️ **Không thể craft hopper** - Chỉ nhận qua lệnh `/vh give`
- ⚠️ **Không thể phá hopper** - Phải dùng Shift+Click để xóa qua GUI
- ⚠️ **Upgrade chỉ dành cho Console** - Người chơi có OP cũng không dùng được
- ⚠️ **Đổi tên bằng anvil không có tác dụng** - Hệ thống kiểm tra chính xác
- ⚠️ **Chỉ owner mới xóa được hopper** - Người khác không thể xóa hopper của bạn
- ⚠️ **Upgrade theo owner** - Hopper sử dụng pickup-range của người đặt

---

## 🐛 Báo Lỗi & Hỗ Trợ

Nếu gặp lỗi hoặc cần hỗ trợ, vui lòng liên hệ:
- 📧 Email: support@example.com
- 💬 Discord: YourDiscord#0000

---

## 📜 License

Copyright © 2024 VineHopper. All rights reserved.

---

<div align="center">

**🌿 VineHopper - Hopper Siêu Mạnh Cho Server Của Bạn! 🌿**

Made with ❤️ by iPaperVN

</div>
