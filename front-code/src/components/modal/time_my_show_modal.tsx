/*
 * ***************************************************************************************
 * author: XiaoLFeng(https://www.x-lf.com)
 * about:
 *   The project contains the source code of com.xlf.schedule.
 *   All source code for this project is licensed under the MIT open source license.
 * licenseStatement:
 *   Copyright (c) 2016-2024 XiaoLFeng. All rights reserved.
 *   For more information about the MIT license, please view the LICENSE file
 *     in the project root directory or visit:
 *   https://opensource.org/license/MIT
 * disclaimer:
 *   Since this project is in the model design stage, we are not responsible for any losses
 *     caused by using this project for commercial purposes.
 *   If you modify the code and redistribute it, you need to clearly indicate what changes
 *     you made in the corresponding file.
 *   If you want to modify it for commercial use, please contact me.
 * ***************************************************************************************
 */

import {DatePicker, Modal} from "antd";
import {useEffect, useState} from "react";
import {ClassTimeEntity} from "../../models/entity/class_time_entity.ts";
import dayjs from "dayjs";

export function TimeMyShowModal({propOpen, classTime, emit}: {
    propOpen: boolean,
    classTime: ClassTimeEntity,
    emit: (data: boolean) => void
}) {
    const {RangePicker} = DatePicker;

    const [open, setOpen] = useState<boolean>(false);
    const [classTimeEntity, setClassTimeEntity] = useState<ClassTimeEntity>({} as ClassTimeEntity);

    useEffect(() => {
        setOpen(propOpen);
    }, [propOpen]);

    useEffect(() => {
        setClassTimeEntity(classTime);
    }, [classTime]);

    function handleOk() {
        emit(false);
    }

    return (
        <Modal
            open={open}
            title={"查看时间表"}
            onOk={handleOk}
            onCancel={() => emit(false)}
            footer={
                <div className="flex gap-1 justify-end text-white">
                    <button
                        onClick={handleOk}
                        className="py-1.5 px-4 rounded-lg shadow bg-sky-500 hover:bg-sky-600 transition"
                    >
                        完成
                    </button>
                </div>
            }
        >
            <div className="text-center">
                <h1 className="text-2xl font-bold">{classTimeEntity.name}</h1>
                <div className={"flex gap-1 justify-center items-center text-gray-500 mb-3"}>
                    {
                        classTimeEntity.is_public ? <span>公开</span> :
                            <span>私有</span>
                    }
                    {
                        classTimeEntity.is_official ? (
                            <>
                                <span>|</span>
                                <span>认证</span>
                            </>
                        ) : null
                    }
                </div>
                <div className={"grid gap-1"}>
                    {
                        classTimeEntity.timetable?.map((time, index) => {
                            return (
                                <div key={index} className={"col-span-full grid grid-cols-12 items-center gap-3"}>
                                    <div
                                        className={"h-full rounded-lg bg-sky-100 text-gray-800 shadow flex justify-center items-center col-span-3"}>
                                        第 {index + 1} 节课时
                                    </div>
                                    <RangePicker picker="time"
                                                 value={[time.startTime ? dayjs(time.startTime, "HH:mm") : undefined, time.endTime ? dayjs(time.endTime, "HH:mm") : undefined]}
                                                 className={"rounded-md border-gray-200 text-gray-800 shadow-sm sm:text-sm flex-1 h-full col-span-9"}
                                                 disabled/>
                                </div>
                            );
                        })
                    }
                </div>
            </div>
        </Modal>
    );
}
