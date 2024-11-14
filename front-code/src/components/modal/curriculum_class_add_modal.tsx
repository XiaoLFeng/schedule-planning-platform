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

import {message, Modal} from "antd";
import {useEffect, useRef, useState} from "react";
import {ClassDTO} from "../../models/dto/class_dto.ts";
import {ListCurriculumTimeEntity} from "../../models/entity/list_curriculum_time_entity.ts";
import {DashOutlined} from "@ant-design/icons";
import {AddClassAPI} from "../../interface/curriculum_api.ts";

export function CurriculumClassAddModal({propOpen, curriculumSelectTime, curriculum, emit, refresh, emitCurriculum}: {
    propOpen: boolean;
    curriculumSelectTime: ListCurriculumTimeEntity;
    curriculum: string;
    emit: (open: boolean) => void;
    refresh: (data: boolean) => void;
    emitCurriculum: (curriculum: string) => void;
}) {
    const [open, setOpen] = useState<boolean>(false);
    const [clazz, setClazz] = useState<ClassDTO>({start_tick: 0, end_tick: 1, day_tick: 1} as ClassDTO);
    const [selectedWeeks, setSelectedWeeks] = useState<boolean[]>(Array(20).fill(true));
    const localTeacher = useRef<string[]>(JSON.parse(localStorage.getItem("local_class_teacher") || "[]") as string[]);

    useEffect(() => {
        setOpen(propOpen);
        setClazz({...clazz, class_grade_uuid: curriculum} as ClassDTO);
        console.debug("propOpen", propOpen);
        console.debug("clazz", clazz);
    }, [curriculum, propOpen]);

    useEffect(() => {
        const weeks = selectedWeeks
            .map((isSelected, index) => (isSelected ? index + 1 : -1))
            .filter((week) => week !== -1);
        setClazz((prevClazz) => ({...prevClazz, weeks}));
    }, [selectedWeeks]);

    async function handleOk() {
        const getResp = await AddClassAPI(clazz);
        if (getResp?.output === "Success") {
            message.success(`课程《${clazz.name}》添加成功`);
            refresh(true);
            emit(false);
            emitCurriculum(curriculum);
        } else {
            message.warning(getResp?.error_message);
        }
    }

    function toggleWeekSelection(index: number) {
        const updatedWeeks = [...selectedWeeks];
        updatedWeeks[index] = !updatedWeeks[index];
        setSelectedWeeks(updatedWeeks);
    }

    return (
        <Modal
            open={open}
            title="添加课程"
            onOk={handleOk}
            onCancel={() => emit(false)}
            footer={
                <div className="flex gap-3 justify-end text-white">
                    <button
                        onClick={() => emit(false)}
                        className="py-1.5 px-4 rounded-lg shadow bg-red-500 hover:bg-red-600 transition"
                    >
                        取消
                    </button>
                    <button
                        onClick={handleOk}
                        className="py-1.5 px-4 rounded-lg shadow bg-sky-500 hover:bg-sky-600 transition"
                    >
                        添加
                    </button>
                </div>
            }
        >
            <div className="grid grid-cols-2 gap-3">
                <div>
                    <label htmlFor="class_name" className="block text-xs font-medium text-gray-700">课程名字</label>
                    <input
                        type="text"
                        id="class_name"
                        placeholder="高等数学II"
                        value={clazz.name}
                        onChange={(e) => setClazz({...clazz, name: e.target.value})}
                        className="mt-1 w-full rounded-md border-gray-200 text-gray-800 shadow-sm sm:text-sm"
                    />
                </div>
                <div>
                    <label htmlFor="class_week" className="block text-xs font-medium text-gray-700">周</label>
                    <select
                        name="class_week"
                        id="class_week"
                        onChange={(e) => setClazz({...clazz, day_tick: Number(e.target.value)} as ClassDTO)}
                        value={clazz.day_tick}
                        className="mt-1 w-full rounded-md border-gray-200 sm:text-sm text-gray-800"
                    >
                        <option value={1}>周一</option>
                        <option value={2}>周二</option>
                        <option value={3}>周三</option>
                        <option value={4}>周四</option>
                        <option value={5}>周五</option>
                        <option value={6}>周六</option>
                        <option value={7}>周日</option>
                    </select>
                </div>
                <div>
                    <label htmlFor="start_time" className="block text-xs font-medium text-gray-700">开始节次</label>
                    <select
                        name="start_time"
                        id="start_time"
                        onChange={(e) => setClazz({...clazz, start_tick: Number(e.target.value)} as ClassDTO)}
                        value={clazz.start_tick}
                        className="mt-1 w-full rounded-md border-gray-200 sm:text-sm text-gray-800"
                    >
                        {curriculumSelectTime.timetable?.map((item, index) => (
                            <option key={index} value={index} selected={index == 0}>{item.start_time}</option>
                        ))}
                    </select>
                </div>
                <div>
                    <label htmlFor="end_time" className="block text-xs font-medium text-gray-700">结束节次</label>
                    <select
                        name="end_time"
                        id="end_time"
                        onChange={(e) => setClazz({...clazz, end_tick: Number(e.target.value)} as ClassDTO)}
                        value={clazz.end_tick}
                        className="mt-1 w-full rounded-md border-gray-200 sm:text-sm text-gray-800"
                    >
                        {curriculumSelectTime.timetable?.map((item, index) => (
                            <option key={index} value={index}>{item.end_time}</option>
                        ))}
                    </select>
                </div>
                <div>
                    <label htmlFor="class_teacher" className="block text-xs font-medium text-gray-700">教师名字</label>
                    <div className="relative mt-1.5">
                        <input
                            type="text"
                            list="class_teacher"
                            id="class_teacher"
                            onChange={(e) => setClazz({...clazz, teacher: e.target.value})}
                            value={clazz.teacher}
                            className="w-full rounded-md border-gray-200 pe-10 text-gray-900 sm:text-sm [&::-webkit-calendar-picker-indicator]:opacity-0"
                            placeholder="输入教师名字"
                        />
                        <span className="absolute inset-y-0 end-0 flex w-8 items-center"><DashOutlined/></span>
                    </div>
                    <datalist id="class_teacher">
                        {localTeacher.current.map((item, index) => (
                            <option key={index} value={item}/>
                        ))}
                    </datalist>
                </div>
                <div>
                    <label htmlFor="class_location" className="block text-xs font-medium text-gray-700">上课位置</label>
                    <div className="relative mt-1.5">
                        <input
                            type="text"
                            list="class_location"
                            id="class_location"
                            onChange={(e) => setClazz({...clazz, location: e.target.value})}
                            value={clazz.location}
                            className="w-full rounded-md border-gray-200 pe-10 text-gray-900 sm:text-sm [&::-webkit-calendar-picker-indicator]:opacity-0"
                            placeholder="敏学楼102"
                        />
                        <span className="absolute inset-y-0 end-0 flex w-8 items-center"><DashOutlined/></span>
                    </div>
                    <datalist id="class_location">
                        {localTeacher.current.map((item, index) => (
                            <option key={index} value={item}/>
                        ))}
                    </datalist>
                </div>
                <fieldset className={"col-span-full"}>
                    <label htmlFor="weeks" className="block text-xs font-medium text-gray-700">上课周次</label>
                    <div className="grid gap-1 grid-cols-5 md:grid-cols-10 mt-1">
                        {selectedWeeks.map((isSelected, week) => (
                            <div
                                key={week}
                                onClick={() => toggleWeekSelection(week)}
                                className={`cursor-pointer transition flex items-center justify-center p-2 rounded-lg border ${
                                    isSelected ? 'bg-blue-500 text-white' : 'bg-gray-100 text-gray-900'
                                }`}
                            >
                                <strong className="text-sm font-thin select-none">{week + 1}</strong>
                            </div>
                        ))}
                    </div>
                </fieldset>
            </div>
        </Modal>
    );
}
