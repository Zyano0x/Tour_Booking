package com.project.tour_booking.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.project.tour_booking.CustomValidator.ValidDepartureDay;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tour")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tour {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NotBlank(message = "Tên tour không được để trống!")
  @Column(name = "name")
  private String name;

  @NotBlank(message = "Ảnh tour không được để trống!")
  @Column(name = "images")
  private List<String> images = new ArrayList<>();

  @NotBlank(message = "Nội dung tour không được để trống!")
  @Column(name = "content")
  private String content;

  @NotBlank(message = "Giá tour không được để trống!")
  @Column(name = "price")
  private BigDecimal price;

  @ValidDepartureDay(message = "Thời gian khởi hàng phải sau thời điểm hiện tại!")
  @NotBlank(message = "Ngày khởi hành không được để trống!")
  @Column(name = "departureDay")
  private LocalDateTime departureDay;

  @NotBlank(message = "Điểm khởi hành không được để trống!")
  @Column(name = "departurePoint")
  private String departurePoint;

  @NotBlank(message = "Ngày đăng không được để trống!")
  @Column(name = "dateOfPosting")
  private LocalDateTime dateOfPosting;
}
