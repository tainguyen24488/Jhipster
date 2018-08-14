import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IUserType } from 'app/shared/model/user-type.model';
import { UserTypeService } from './user-type.service';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-user-type-update',
    templateUrl: './user-type-update.component.html'
})
export class UserTypeUpdateComponent implements OnInit {
    private _userType: IUserType;
    isSaving: boolean;

    users: IUser[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private userTypeService: UserTypeService,
        private userService: UserService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ userType }) => {
            this.userType = userType;
        });
        this.userService.query().subscribe(
            (res: HttpResponse<IUser[]>) => {
                this.users = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.userType.id !== undefined) {
            this.subscribeToSaveResponse(this.userTypeService.update(this.userType));
        } else {
            this.subscribeToSaveResponse(this.userTypeService.create(this.userType));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IUserType>>) {
        result.subscribe((res: HttpResponse<IUserType>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
    get userType() {
        return this._userType;
    }

    set userType(userType: IUserType) {
        this._userType = userType;
    }
}
