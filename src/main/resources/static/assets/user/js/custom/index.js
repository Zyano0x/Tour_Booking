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

////////////////////////////////////////

/*FAVOURITE TOURS*/
async function renderFavouriteTours(tours, fatherBlock) {
  const favouriteTours = tours.filter(tour => tour.status && tour.isHot);
  if (favouriteTours.length > 0) {
    for (const favouriteTour of favouriteTours) {
      let html = `<div class="col-lg-4 col-md-6 wow zoomIn" data-wow-delay="0.2s">
            <div div class="tour_container" >
              <div class="ribbon_3 popular"><span>Nổi bật</span></div>
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
                    ><span class="price"><sup>VND</sup>${moneyFormat(favouriteTour.priceForAdult, true)}</span>
                  </div>
                </a>
              </div>
              <div class="tour_title">
                <h3>
                  <strong>${favouriteTour.name}</strong>
                </h3>
                <div id="rating${favouriteTour.id}" class="rating">
                  ${await renderRating(`/api/tour/${favouriteTour.id}/tour-reviews`)}
              </div >
            </div >
          </div >
        </div >`;
      if (html != '')
        fatherBlock.insertAdjacentHTML("beforeend", html);
    }
  }
}
/*END FAVOURITE TOURS*/

////////////////////////////////////////

/*FAVOURITE DESTINATIONS*/
function renderFavouriteDestinations(destinations) {
  try {
    const favouriteDestinationsBlock = document.querySelector("#favourite-destinations");
    if (favouriteDestinationsBlock) {
      for (const destination of destinations) {
        let html = destination.status && destination.isHot ?
          `<div class="col-lg-4 col-md-6 wow zoomIn" data-wow-delay="0.1s">
              <div class="hotel_container">
                <div class="ribbon_3 popular"><span>Nổi bật</span></div>
                <div class="img_container">
                  <a href="single_hotel.html" class="destination-link${destination.id}">
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
        document
          .querySelector(`.destination-link${destination.id}`)
          .addEventListener("click", function (event) {
            event.preventDefault();

            // Lưu trạng thái vào local storage
            localStorage.setItem("destinationFilter", `${destination.id}`);

            // Chuyển đến trang tours
            window.location.href = "/tours";
          });
      }
    } else
      throw new Error("Element with id '#favourite-destinations' not found in the DOM");
  } catch (error) {
    console.log(">>> Error: " + error.message)
  }
}
/*END FAVOURITE DESTINATIONS*/

////////////////////////////////////////

/*NEW ARTICLES*/
async function renderNewArticles(articles, fatherBlock) {
  const newArticles = articles.filter(article => article.status);
  if (newArticles.length > 0) {
    let count = 0;
    for (const newArticle of newArticles) {
      let html =
        `<div class="col-lg-4 col-md-6 wow zoomIn" data-wow-delay="0.2s">
            <div div class="tour_container" >
              <div class="ribbon_3 popular"><span>Mới</span></div>
              <div class="img_container">
                <a href="/articles/${newArticle.id}">
                  <img
                    src="${newArticle.thumbnail}"
                    width="800"
                    height="533"
                    alt="Image"
                  />
                  <div class="short_info">
                    <i class="icon_set_1_icon-43"></i
                    >
                  </div>
                </a>
              </div>
              <div class="tour_title">
                <h3>
                  <strong>${newArticle.title}</strong>
                </h3>
                <div id="rating${newArticle.id}" class="rating">
                  ${await renderRating(`/api/article/${newArticle.id}/article-reviews`)}
              </div >
            </div >
          </div >
        </div >`;
      if (html != '')
        fatherBlock.insertAdjacentHTML("beforeend", html);
      if (count++ == 6)
        break;
    }
  }
}
/*END NEW ARTICLES*/

////////////////////////////////////////

import { getApi, handleGetData, renderRating, moneyFormat } from './global_function.js';

function start() {
  getApi("/api/sliders", renderSlider);
  handleGetData("/api/tours", renderFavouriteTours, "#favourite-tours");
  getApi("/api/destinations", renderFavouriteDestinations);
  handleGetData("/api/articles/all", renderNewArticles, "#new-articles");
}

document.addEventListener("DOMContentLoaded", function () {
  start();
});
