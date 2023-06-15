import { AfterViewInit, Component, ElementRef, OnDestroy, ViewChild } from '@angular/core';
// eslint-disable-next-line import/no-extraneous-dependencies
import { Feature, Map, View } from 'ol';
import olms from 'ol-mapbox-style';
import { fromLonLat, transform } from 'ol/proj';
import { Point } from 'ol/geom';
import VectorLayer from 'ol/layer/Vector';
import VectorSource from 'ol/source/Vector';
import { Subscription } from 'rxjs';
import { CollarService } from '../../services/collar.service';

@Component({
  selector: 'app-pet-geo-map',
  templateUrl: './pet-geo-map.component.html',
  styleUrls: ['./pet-geo-map.component.scss'],
})
export class PetGeoMapComponent implements AfterViewInit, OnDestroy {
  map: Map | undefined;

  @ViewChild('map')
  mapContainer: ElementRef<HTMLElement>;

  private sub: Subscription | undefined;

  constructor(private collarService: CollarService) {
    this.mapContainer = {} as ElementRef;
  }

  ngAfterViewInit() {
    const initialState = {
      lng: 27.59502,
      lat: 53.91176,
      zoom: 17,
    };

    const myAPIKey = '234bec3141fd4752a1074a210d4889ca';
    const mapStyle = 'https://maps.geoapify.com/v1/styles/osm-bright/style.json';

    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    olms(this.mapContainer.nativeElement, `${mapStyle}?apiKey=${myAPIKey}`).then((map: Map) => {
      map.setView(
        new View({
          center: transform([initialState.lng, initialState.lat], 'EPSG:4326', 'EPSG:3857'),
          zoom: initialState.zoom,
        }),
      );
      this.map = map;
    });

    // eslint-disable-next-line @typescript-eslint/ban-ts-comment
    // @ts-ignore
    this.sub = this.collarService.getPetGeo().subscribe((data) => {
      const marker = new VectorLayer({
        source: new VectorSource({
          features: [new Feature({ geometry: new Point(fromLonLat([data.lng, data.lat])) })],
        }),
      });
      if (!this.map) {
        console.log('error map');
      } else {
        this.map.addLayer(marker);
      }

      // eslint-disable-next-line @typescript-eslint/no-implied-eval
      setTimeout(() => {
        this.map?.removeLayer(marker);
      }, 300);
    });
  }

  ngOnDestroy() {
    console.log('sub is over');
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }
}
