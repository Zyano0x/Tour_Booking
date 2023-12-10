/*SLIDER*/
function renderSlider(sliders) {
  try {
    const headerVideoMediaBlock = document.querySelector("#header-video--media");
    if (headerVideoMediaBlock) {
      const displaySlider = sliders.find((slider) => slider.status);
      if (displaySlider)
        headerVideoMediaBlock.setAttribute("data-teaser-source", `/assets/user/video/${displaySlider.path}`);

      HeaderVideo.init({
        container: $(".header-video"),
        header: $(".header-video--media"),
        videoTrigger: $("#video-trigger"),
        autoPlayVideo: false,
      });
    } else
      throw new Error("Element with id '#headerVideoMedia' not found in the DOM");
  } catch (error) {
    console.log(">>> Error: " + error.message)
  }
}
/*END SLIDER*/

/*FAVOURITE TOURS*/
async function renderFavouriteTours(tours, fatherBlock) {
  const favouriteTours = tours.filter(tour => tour.status && tour.isHot);
  if (favouriteTours.length > 0) {
    for (const favouriteTour of favouriteTours) {
      let html = favouriteTour.status && favouriteTour.isHot ?
        `<div class="col-lg-4 col-md-6 wow zoomIn" data-wow-delay="0.2s">
            <div div class="tour_container" >
              <div class="ribbon_3 popular"><span>Popular</span></div>
              <div class="img_container">
                <a href="/tours/${favouriteTour.id}">
                  <img
                    src="${favouriteTour.thumbnail}"
                    width="800"
                    height="533"
                    alt="Image"
                  />
                  <div class="short_info">
                    <i class="icon_set_1_icon-43"></i
                    ><span class="price"><sup>VND</sup>${favouriteTour.priceForAdult}.0</span>
                  </div>
                </a>
              </div>
              <div class="tour_title">
                <h3>
                  <strong>${favouriteTour.name}</strong>
                </h3>
                <div id="tour_rating${favouriteTour.id}" class="tour_rating">
                  ${await renderToursRating(favouriteTour.id)}
              </div >
            </div >
          </div >
        </div >`
        :
        '';
      if (html != '')
        fatherBlock.insertAdjacentHTML("beforeend", html);
    }
  }
}
/*END FAVOURITE TOURS*/

/*FAVOURITE DESTINATIONS*/
function renderFavouriteDestinations(destinations) {
  try {
    const favouriteDestinationsBlock = document.querySelector("#favourite-destinations");
    if (favouriteDestinationsBlock) {
      for (const destination of destinations) {
        let html = destination.status && destination.isHot ?
          `<div class="col-lg-4 col-md-6 wow zoomIn" data-wow-delay="0.1s">
              <div class="hotel_container">
                <div class="ribbon_3 popular"><span>Popular</span></div>
                <div class="img_container">
                  <a href="single_hotel.html">
                    <img src="${destination.thumbnail}" width="800" height="533"  alt="image">
                  </a>
                </div>
                <div class="hotel_title">
                  <h3><strong>${destination.name}</strong></h3>
                  <!-- end rating -->
                </div>
              </div>
              <!-- End box -->
            </div>
            <!-- End col -->`
          : ''
        if (html != '')
          favouriteDestinationsBlock.insertAdjacentHTML("afterbegin", html);
      }
    } else
      throw new Error("Element with id '#favourite-destinations' not found in the DOM");
  } catch (error) {
    console.log(">>> Error: " + error.message)
  }
}
/*END FAVOURITE DESTINATIONS*/

import { getApi, getDropList, renderSearchDropList, handleGetTours, renderToursRating, compareDate } from './global_function.js';

function start() {
  getApi("/api/slider/all", renderSlider);
  getDropList("/api/destinations", renderSearchDropList, "#destinationsDropList", "walking.png");
  getDropList("/api/types-of-tours", renderSearchDropList, "#typeOfTourDropList", "all_tours.png");
  handleGetTours(renderFavouriteTours, "#favourite-tours");
  getApi("/api/destinations", renderFavouriteDestinations);
}

document.addEventListener("DOMContentLoaded", function () {
  start();
});