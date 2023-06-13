import {AfterViewInit, Component} from '@angular/core';
import * as L from "leaflet";

@Component({
  selector: 'app-pet-geo-map',
  templateUrl: './pet-geo-map.component.html',
  styleUrls: ['./pet-geo-map.component.scss']
})
export class PetGeoMapComponent implements AfterViewInit {
  private map: L.Map | undefined;

  private initMap(): void {
    // this.map = L.map('map', {
    //   center: [39.8282, -98.5795],
    //   zoom: 3
    // });
    //
    // const tiles = L.tileLayer();
    //
    // tiles.addTo(this.map);

    this.map = L.map('map', {attributionControl: false}).setView([0, 0], 1);
    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      maxZoom: 18,
      minZoom: 3,
      attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(this.map);


    this.map.flyTo([39.8282, -98.5795], 13);

    const icon = L.icon({
      iconUrl: 'assets/images/marker-icon.png',
      shadowUrl: 'assets/images/marker-shadow.png',
      popupAnchor: [13, 0]
    });

    const marker = L.marker([39.8282, -98.5795], {icon}).bindPopup('Angular Leaflet');
    marker.addTo(this.map);
  }

  ngAfterViewInit(): void {
    this.initMap();
  }
}
