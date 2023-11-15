package com.project.tour_booking.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.tour_booking.CustomValidator.ValidDepartureDay;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "tour")
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor
public class Tour {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @NonNull
  @NotBlank(message = "Tên tour không được để trống!")
  @Column(name = "name")
  private String name;

  @NonNull
  @NotBlank(message = "Nội dung tour không được để trống!")
  @Column(name = "content")
  private String content;

  @NonNull
  @NotNull(message = "Giá tour không được để trống!")
  @Column(name = "price")
  private BigDecimal price;

  @NonNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @ValidDepartureDay(message = "Thời gian khởi hàng phải sau thời điểm hiện tại!")
  @NotNull(message = "Ngày khởi hành không được để trống!")
  @Column(name = "departureDay")
  private LocalDateTime departureDay;

  @NonNull
  @NotBlank(message = "Điểm khởi hành không được để trống!")
  @Column(name = "departurePoint")
  private String departurePoint;

  @NonNull
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss") // Chủ động format vì LocalDateTime không tự động format
  @NotNull(message = "Ngày đăng không được để trống!")
  @Column(name = "dateOfPosting")
  private LocalDateTime dateOfPosting;

  @Transient // Không tạo thành cột trong bảng
  @NonNull
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  @NotEmpty(message = "Ảnh tour không được để trống!")
  private List<String> images = new ArrayList<>();

  @JsonIgnore
  @OneToMany(mappedBy = "tour", cascade = CascadeType.ALL)
  private List<TourImage> tourImages;

  @ManyToOne(optional = false)
  @JoinColumn(name = "type_of_tour_id", referencedColumnName = "id")
  private TypeOfTour typeOfTour;
}
