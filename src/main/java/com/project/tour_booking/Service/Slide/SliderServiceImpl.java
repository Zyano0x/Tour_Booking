package com.project.tour_booking.Service.Slide;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.project.tour_booking.DTO.SliderDTO;
import com.project.tour_booking.Entity.Slider;
import com.project.tour_booking.Exception.SliderNotFoundException;
import com.project.tour_booking.Repository.SliderRepository;

import lombok.AllArgsConstructor;

@Service
@RequiredArgsConstructor
public class SliderServiceImpl implements SliderService {
    private final SliderRepository sliderRepository;
    private final ModelMapper modelMapper;

    @Override
    public Slider savSlider(SliderDTO sliderDTO) {
        Slider slider = modelMapper.map(sliderDTO, Slider.class);
        slider.setStatus(false);
        sliderRepository.save(slider);
        return slider;
    }

    @Override
    public Slider getSlider(Long sliderId) {
        Optional<Slider> sliderOptional = sliderRepository.findById(sliderId);
        if (sliderOptional.isPresent()) {
            return sliderOptional.get();
        } else {
            throw new SliderNotFoundException(sliderId);
        }
    }

    @Override
    public List<Slider> getSliders() {
        return (List<Slider>) sliderRepository.findAll();
    }

    @Override
    public Slider updateSlider(Slider slider, Long sliderId) {
        Optional<Slider> sliderOptional = sliderRepository.findById(sliderId);
        if (sliderOptional.isPresent()) {
            Slider updateSlider = sliderOptional.get();
            updateSlider.setPath(slider.getPath());
            updateSlider.setStatus(slider.getStatus());
            return sliderRepository.save(updateSlider);
        } else {
            throw new SliderNotFoundException(sliderId);
        }
    }

    @Override
    public Slider updateSliderStatus(Long sliderId) {
        Optional<Slider> sliderOptional = sliderRepository.findById(sliderId);
        if (sliderOptional.isPresent()) {
            if (!sliderOptional.get().getStatus()) {
                List<Slider> sliders = (List<Slider>) sliderRepository.findAll();
                for (Slider slider : sliders) {
                    slider.setStatus(false);
                    sliderRepository.save(slider);
                }
            }
            sliderOptional.get().setStatus(!sliderOptional.get().getStatus());
            sliderRepository.save(sliderOptional.get());
            return sliderOptional.get();
        } else {
            throw new SliderNotFoundException(sliderId);
        }
    }
}
