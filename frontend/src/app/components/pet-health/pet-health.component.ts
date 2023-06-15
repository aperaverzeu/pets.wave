import { Component, OnDestroy, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Subscription } from 'rxjs';
import { MessageService } from 'primeng/api';
import { CollarService } from '../../services/collar.service';
import { Health } from '../../models/health';

@Component({
  selector: 'app-pet-health',
  templateUrl: './pet-health.component.html',
  styleUrls: ['./pet-health.component.scss'],
})
export class PetHealthComponent implements OnInit, OnDestroy {
  name: string;

  health: Health;

  private sub: Subscription | undefined;

  constructor(
    private location: Location,
    private collarService: CollarService,
    private messageService: MessageService,
  ) {
    this.name = sessionStorage.getItem('actual-pet-name')!;
    this.health = {
      bpm: '67',
      spo2: '98',
      stressLevel: 'Низкий',
    };
  }

  ngOnInit() {
    this.sub = this.collarService.getPetHealth().subscribe({
      next: (data) => {
        console.log(data);
        this.health = data;
        if (data.stressLevel === 'Высокий') {
          this.messageService.add({
            severity: 'warn',
            summary: 'Внимание',
            detail: 'Высокий уровень стресса',
          });
        }
      },
      error: (err) => {
        console.error(err);
      },
    });
  }

  ngOnDestroy() {
    console.log('sub is over');
    if (this.sub) {
      this.sub.unsubscribe();
    }
  }

  back() {
    this.location.back();
  }
}
