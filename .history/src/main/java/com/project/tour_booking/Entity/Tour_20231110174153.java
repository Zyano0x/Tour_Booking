package com.project.tour_booking.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.tour_booking.CustomValidator.ValidDepartureDay;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

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

  @NotBlank(message = "Nội dung tour không được để trống!")
  @Column(name = "content")
  private String content;

  @NotNull(message = "Giá tour không được để trống!")
  @Column(name = "price")
  private BigDecimal price;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @ValidDepartureDay(message = "Thời gian khởi hàng phải sau thời điểm hiện tại!")
  @NotNull(message = "Ngày khởi hành không được để trống!")
  @Column(name = "departureDay")
  private LocalDateTime departureDay;

  @NotBlank(message = "Điểm khởi hành không được để trống!")
  @Column(name = "departurePoint")
  private String departurePoint;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @NotNull(message = "Ngày đăng không được để trống!")
  @Column(name = "dateOfPosting")
  private LocalDateTime dateOfPosting;

  @NotEmpty(message = "Ảnh tour không được để trống!")
  // @Column(name = "images", columnDefinition = "json")
  private List<String> images = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
  private List<TourImage> tourImages;
}
