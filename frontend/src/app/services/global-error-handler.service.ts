import {ErrorHandler, Injectable, Injector} from "@angular/core";
import {MessageService} from "primeng/api";
import {Router} from "@angular/router";

@Injectable()
export class GlobalErrorHandler implements ErrorHandler {
  constructor(private injector: Injector,
              private messageService: MessageService) {
  }

  handleError(error: any) {
    let router = this.injector.get(Router);
    this.messageService.add({severity: 'error', summary: 'Ошибка', detail: 'Произошла ошибка'});
    router.navigate([router.url]).finally();
  }
}
