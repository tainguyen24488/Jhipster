import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IUserType } from 'app/shared/model/user-type.model';

@Component({
    selector: 'jhi-user-type-detail',
    templateUrl: './user-type-detail.component.html'
})
export class UserTypeDetailComponent implements OnInit {
    userType: IUserType;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ userType }) => {
            this.userType = userType;
        });
    }

    previousState() {
        window.history.back();
    }
}
